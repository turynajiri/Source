package App;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Jiri.Turyna
 */
public interface UserInterface {
    public boolean check(String username, String password) throws FileNotFoundException, IOException;
    public void loadUserData() throws FileNotFoundException, IOException;
    public boolean checkIfExists(String username);
    public void login(String username) throws IOException;
    public void createNewUser(String username, String password) throws IOException;
    public String getStatusByUsername(String username);
    public String getPasswordByUsername(String username);
    public String getIdByUsername(String username);
    public void changePassword(String name, String newPass) throws IOException;
    public void improveStatus(String name) throws IOException;
    public String watchMovie(String movie, String username) throws IOException;

}
