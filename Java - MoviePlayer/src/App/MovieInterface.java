package App;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Jiri.Turyna
 */
public interface MovieInterface {
    public String listByGenre(String genre) throws IllegalArgumentException, IOException;
    public String listByYear(String status);
    public String listByRating(String rating) throws IllegalArgumentException, IOException;
    public String printListofMovies();
    public void load(String status) throws FileNotFoundException, IOException;
    public String getMovieById(int movie);
    public void playSound(String movie) throws IOException;
}
