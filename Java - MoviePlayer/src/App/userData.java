package App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Exceptions;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jiri.Turyna
 */
public class userData implements UserInterface {

    Exceptions ex = new Exceptions();
    private ArrayList<User> data = new ArrayList<>();

    // Volá se při vytváření nového účtu
    // Kontroluje jestli už neexistuje uživatel se stejným Username
    public boolean checkIfExists(String username) {
        for (User u : data) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Zapisuje do souboru UserActions.txt username a akci
    public void writeToFile(String username, String action) throws IOException {
        LocalDateTime now = LocalDateTime.now();

        File f = new File("data/UserActions.txt");
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(f, true));
            writer.write("\n" + username + " " + action + " " + now);
            writer.close();
        } catch (IOException e) {
            ex.writeToFile("IOException", action);
        }
    }

    // Volá writeToFile s akcí "Logged in"
    public void login(String username) throws IOException {
        String action = "Logged in";
        try {
            writeToFile(username, action);
        } catch (IOException e) {
            ex.writeToFile("IOException", action);
        }
    }

    // Volá se před loginem 
    // Kontroluje jestli se shodej uživatelské jméno a heslo
    public boolean check(String username, String password) throws FileNotFoundException, IOException {
        // true if matched
        empty();
        loadUserData();
        for (User u : data) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        try {
            ex.loginError(username);
        } catch (IOException e) {
            ex.writeToFile("IOException", "check");

        }
        return false;
    }

    // Vytvoří nového uživatele a uloží ho do souboru
    public void createNewUser(String username, String password) throws IOException {
        String status = "n";
        String action = "New user created";

        try {
            File f = new File("data/Accounts.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            FileWriter fileWriter = new FileWriter(f, true);
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            String str = (lines + " " + username + " " + status + " " + password + "\n");
            fileWriter.write(str);
            fileWriter.close();

            writeToFile(username, action);
        } catch (Exception e) {
            ex.writeToFile("IOException", "creating new user");
        }
    }

    // Načte všechny uživatelská data ze souboru Accounts.txt
    public void loadUserData() throws FileNotFoundException, IOException {
        empty();

        File f;
        Scanner sc;

        int id;
        String username;
        String status;
        String password;

        f = new File("data/Accounts.txt"); // File separator
        if (!f.exists()) {
            try {
                ex.FileNotFound(f.toString());
            } catch (IOException e) {
                ex.writeToFile("IOException", "loadUserData");
            }
        }
        try {
            sc = new Scanner(f);
            while (sc.hasNext()) {
                id = sc.nextInt();
                username = sc.next();
                status = sc.next();
                password = sc.next();
                User u = new User(status, username, id, password);
                data.add(u);
            }
        } catch (FileNotFoundException e) {
            ex.writeToFile("Scanner error", "FileNotFound");
            throw new FileNotFoundException("Scanner error " + f.toString());
        }
    }

    // Vrací status uživatele podle jeho uživatelského jména
    public String getStatusByUsername(String username) {
        StringBuilder sb = new StringBuilder();
        for (User u : data) {
            if (u.getUsername().equals(username)) {
                String tmp = u.getStatus();
                sb.append(tmp);
            }
        }
        return sb.toString();
    }

    // Vrací heslo uživatele polde jeho uživatelského jména
    public String getPasswordByUsername(String username) {
        StringBuilder sb = new StringBuilder();
        for (User u : data) {
            if (u.getUsername().equals(username)) {
                String tmp = u.getPassword();
                sb.append(tmp);
            }
        }
        return sb.toString();
    }

    // Vrací Id uživatele podle jeho uživatelského jména
    public String getIdByUsername(String username) {
        StringBuilder sb = new StringBuilder();
        for (User u : data) {
            if (u.getUsername().equals(username)) {
                int tmp = u.getId();
                sb.append(tmp);
            }
        }
        return sb.toString();
    }

    // Mění heslo uživatele a zapisuje ho zpět do souboru Accounts.txt
    public void changePassword(String username, String password) throws IOException {
        Path inputFilePath = Paths.get("data/Accounts.txt");
        Path tempfilePath = Paths.get("data/tmp.txt");
        File inputFile = new File("data/Accounts.txt");
        File tempFile = new File("data/tmp.txt");

        String action = "Password changed";
        List<String> lines = new ArrayList<>();

        try { // Try with resources

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int i = 0;

            String toRemove = getIdByUsername(username);
            while ((line = reader.readLine()) != null) {
                if (line.contains(toRemove + " ")) {
                    line = (i + " " + username + " " + getStatusByUsername(username) + " " + password);
                }
                writer.write(line + "\n");
                i++;
            }

            reader.close();
            writer.close();

            System.gc();

            //inputFile.delete();
            //tempFile.renameTo(inputFile);
            
            Files.deleteIfExists(Paths.get("data/Accounts.txt"));
            Files.copy(Paths.get("data/tmp.txt"), Paths.get("data/Accounts.txt"));
            Files.deleteIfExists(Paths.get("data/tmp.txt"));
            writeToFile(username, action);
        } catch (IOException x) {
            // File permission problems are caught here.
            ex.writeToFile("IOException", "Change password");
        }
    }

    // Zlepší status uživatele z Normal User na Premium User a zápiše změnu do souboru Accounts.txt
    public void improveStatus(String username) throws IOException {
        Path inputFilePath = Paths.get("data/Accounts.txt");
        Path tempfilePath = Paths.get("data/tmp.txt");
        File inputFile = new File("data/Accounts.txt");
        File tempFile = new File("data/tmp.txt");

        String action = "Status improved";
        List<String> lines = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int i = 0;

            String toRemove = getIdByUsername(username);
            while ((line = reader.readLine()) != null) {
                if (line.contains(toRemove + " ")) {
                    line = (i + " " + username + " " + "p" + " " + getPasswordByUsername(username));
                }
                writer.write(line + "\n");
                i++;
            }

            reader.close();
            writer.close();

            System.gc();

            Files.deleteIfExists(Paths.get("data/Accounts.txt"));
            Files.copy(Paths.get("data/tmp.txt"), Paths.get("data/Accounts.txt"));
            Files.deleteIfExists(Paths.get("data/tmp.txt"));

            writeToFile(username, action);
        } catch (IOException x) {
            // File permission problems are caught here.
            ex.writeToFile("IOException", "Improve status");

        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        userData data = new userData();
        data.loadUserData();
        data.improveStatus("Jirka");
    }

    // Volá writeToFile s akcí WatchMovie
    public String watchMovie(String movie, String username) throws IOException {

        String action = "Watching a movie" + movie;
        writeToFile(username, action);
        return "You are watching movie: " + movie;
    }

    public void empty() {
        data.clear();
    }
}
