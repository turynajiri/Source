package App;

/**
 *
 * @author Jiri.Turyna
 */
public class Movie {
    private String name;
    private String genre;
    private int releasedYear;
    private double rating;
    private int id;

    public Movie(String name, String genre, int releasedYear, double rating, int id) {
        this.name = name;
        this.genre = genre;
        this.releasedYear = releasedYear;
        this.rating = rating;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public double getRating() {
        return rating;
    }
    
    public int getid(){
        return id;
    }

    @Override
    public String toString() {
        return id + "." + name + ", " + genre + ", " + releasedYear + ", " + rating + "%";
    }
    
}
