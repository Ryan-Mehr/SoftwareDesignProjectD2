package Repository;

import Classes.User;
import DAO.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
    // Make it singleton.
    // https://www.geeksforgeeks.org/system-design/singleton-design-pattern/
    private static volatile UserRepository obj = null;
    private UserRepository() {
        System.out.println("UserRepository is instantiated.");
    }
    public static UserRepository getInstance() {
        if (obj == null) {
            synchronized (UserRepository.class) {
                if (obj == null) {
                    obj = new UserRepository();
                }
            }
        }
        return obj;
    }

    // Make sure DAOs are singletons as well.
    private final UserDAO userDAO = UserDAO.getInstance();

    public boolean validateInput(String username, String password) {
        // True if valid, false if not.
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkIfUsernameExists(String username) throws SQLException {
        return userDAO.checkIfUsernameExists(username);
    }

    public boolean registerUser(String username, String password) throws SQLException {
        if (validateInput(username, password)) {
            return userDAO.save(username, password);
        }
        return false;
    }

    public User loginUser(String username,String password) throws SQLException {
        // Any business logic here like input validation if needed.
        return userDAO.login(username, password);
    }
}
