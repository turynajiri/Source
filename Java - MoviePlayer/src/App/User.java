package App;

import java.io.FileNotFoundException;
import java.io.IOException;
import utils.Exceptions;

/**
 *
 * @author Jiri.Turyna
 */
public class User {

    Exceptions ex = new Exceptions();

    private String status;
    private String username;
    private int Id;
    private String password;

    public User(String status, String username, int Id, String password) {
        this.status = status;
        this.username = username;
        this.Id = Id;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return Id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" + ", status=" + status + ", username=" + username + ", Id=" + Id + ", password=" + password + '}';
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        User u = new User("n", "Karel", 1, "asd");
    }
}
