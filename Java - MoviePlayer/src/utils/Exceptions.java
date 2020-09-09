package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

/**
 *
 * @author Jiri.Turyna
 */
public class Exceptions {

    LocalDateTime now = LocalDateTime.now();

    public void loginError(String username) throws java.io.IOException {
        String action = "Error during login ";
        writeToFile(action, username);

        System.out.println("\n" + "Error!" + "\n" + "Credentials do not match. Try again");
    }

    public void FileNotFound(String file) throws java.io.IOException {
        String action = "File not found ";
        writeToFile(action, file);

        System.out.println(action);
    }

    public void InvalidUserArgument(String status) throws java.io.IOException {
        String action = "Wrong user argument ";
        writeToFile(action, status);

        System.out.println(action);
    }

    public void MovieNotFound(String movie) throws java.io.IOException {
        String action = "Movie not found ";
        writeToFile(action, movie);

        System.out.println(action);
    }

    public void GenreNotFound(String genre) throws java.io.IOException {
        String action = "Genre not found ";
        writeToFile(action, genre);
        System.out.println(action);
    }

    public void IOException() throws java.io.IOException {
        String action = "IOException";
        writeToFile(action);
        System.out.println(action);
    }

    public void wrongRating() throws java.io.IOException {
        String action = "Wrong rating";
        writeToFile(action);
        System.out.println("Rating has to be from 0-100");
    }

    public void OldPasswordNotMatched() throws java.io.IOException {
        String action = "Old password was incorrect";
        writeToFile(action);
        System.out.println(action);
    }

    public void writeToFile(String action, String something) throws java.io.IOException {
        File f = new File("data/Errors.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
        writer.write("\n" + action + " " + something + " " + now);
        writer.close();
    }

    public void writeToFile(String action) throws java.io.IOException {
        File f = new File("data/Errors.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
        writer.write("\n" + action + " " + now);
        writer.close();
    }
}
