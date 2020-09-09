package App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import utils.Exceptions;

/**
 *
 * @author Jiri.Turyna
 */
public class MovieData implements MovieInterface {

    private Exceptions ex = new Exceptions();
    private ArrayList<Movie> normalMovieData = new ArrayList<>();
    private ArrayList<Movie> premiumMovieData = new ArrayList<>();

    public void playSound(String movie) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("Data/Sounds/" + movie + ".wav");
        String tmp = sb.toString().replaceAll("\\s+", "");

        File soundFile = new File(tmp);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            try {
                ex.FileNotFound(movie);
            } catch (IOException ee) {
                ex.writeToFile("IOException", "Play sound");
            }
        }
    }

    public String listByGenre(String genre) throws IllegalArgumentException, IOException {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (Movie m : normalMovieData) {
            if (m.getGenre().equals(genre)) {
                sb.append(m.getName() + "\n");
                found = true;
            }
        }

        for (Movie m : premiumMovieData) {
            if (m.getGenre().equals(genre)) {
                sb.append(m.getName() + "\n");
                found = true;
            }
        }

        if (!found) {
            try {
                ex.GenreNotFound(genre);
            } catch (IOException e) {
                ex.writeToFile("IOException", "List by genre");
            }
        }

        return sb.toString();
    }

    public String listByYear(String status) {
        StringBuilder sb = new StringBuilder();
        if (status.equals("n")) {
            ArrayList<Movie> sortedData = normalMovieData;
            Collections.sort(sortedData, Collections.reverseOrder(Comparator.comparing(Movie::getReleasedYear))); // Stream api

            for (Movie m : normalMovieData) {
                sb.append(m.getName() + " " + m.getReleasedYear() + "\n");
            }
        } else {
            ArrayList<Movie> sortedData = premiumMovieData;
            Collections.sort(sortedData, Collections.reverseOrder(Comparator.comparing(Movie::getReleasedYear)));

            for (Movie m : premiumMovieData) {

                sb.append(m.getName() + " " + m.getReleasedYear() + "\n");
            }
        }

        return sb.toString();
    }

    public String listByRating(String rating) throws IllegalArgumentException, IOException {
        StringBuilder sb = new StringBuilder();

        try {
            int rat = Integer.parseInt(rating);
            if (rat < 0 || rat > 100) {
                ex.wrongRating();
            } else {
                for (Movie m : normalMovieData) {
                    if (m.getRating() >= rat) {
                        sb.append(m.getName() + " " + m.getRating() + "\n");
                    }
                }

                for (Movie m : premiumMovieData) {
                    if (m.getRating() >= rat) {
                        sb.append(m.getName() + " " + m.getRating() + "\n");
                    }
                }
            }
        } catch (Exception e) {
            try {
                ex.wrongRating();
            } catch (IOException ee) {
                ex.writeToFile("IOException", " List by rating");
            }
        }
        return sb.toString();
    }

    public void load(String status) throws FileNotFoundException, IOException {
        empty();

        File f;
        Scanner sc;

        String name;
        int releasedYear;
        String genre;
        double rating;

        if (status == "n") {
            f = new File("data/NormalMovies.txt");
            if (f == null) {
                try {
                    ex.FileNotFound(f.toString());
                } catch (IOException e) {
                    ex.writeToFile("IOException", "load");

                }

            } else {
                sc = new Scanner(f);
                int i = 0;
                while (sc.hasNext()) {
                    genre = sc.next();
                    releasedYear = sc.nextInt();
                    rating = sc.nextDouble();
                    name = sc.nextLine();

                    Movie m = new Movie(name, genre, releasedYear, rating, i);
                    i++;
                    normalMovieData.add(m);
                }
            }
        } else if (status == "p") {

            f = new File("data/PremiumMovies.txt");
            if (f == null) {
                try {
                    ex.FileNotFound(f.toString());
                } catch (IOException ex) {
                    System.out.println("IOException");
                }
            } else {
                sc = new Scanner(f);
                int i = 0;

                while (sc.hasNext()) {
                    genre = sc.next();
                    releasedYear = sc.nextInt();
                    rating = sc.nextDouble();
                    name = sc.nextLine();

                    Movie m = new Movie(name, genre, releasedYear, rating, i);
                    i++;
                    premiumMovieData.add(m);
                }
            }

        } else {
            try {
                ex.InvalidUserArgument(status);
            } catch (IOException e) {
                ex.writeToFile("IOException", "load");
            }
        }
    }

    public String printListofMovies() {
        StringBuilder sb = new StringBuilder();
        for (Movie m : normalMovieData) {
            sb.append(m);
            sb.append("\n");
        }
        for (Movie m : premiumMovieData) {
            sb.append(m);
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getMovieById(int id) {
        StringBuilder sb = new StringBuilder();
        for (Movie m : normalMovieData) {
            if (m.getid() == id) {
                sb.append(m.getName());
            }
        }
        for (Movie m : premiumMovieData) {
            if (m.getid() == id) {
                sb.append(m.getName());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        MovieData data = new MovieData();
        data.load("n");
        System.out.println(data.getMovieById(1));
    }

    public void empty() {
        normalMovieData.clear();
        premiumMovieData.clear();
    }

}
