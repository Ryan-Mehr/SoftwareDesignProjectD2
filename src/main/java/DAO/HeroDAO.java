package DAO;

import Classes.Heros.Hero;
import Classes.User;
import Repository.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroDAO {
    private static volatile HeroDAO obj = null;
    private HeroDAO() {
        System.out.println("HeroDAO is instantiated.");
    }
    public static HeroDAO getInstance() {
        if (obj == null) {
            synchronized (HeroDAO.class) {
                if (obj == null) {
                    obj = new HeroDAO();
                }
            }
        }
        return obj;
    }

//    public boolean save(String username, String password) throws SQLException {
//        String query = "INSERT INTO USERS (username, password) VALUES (?, ?)";
//        boolean ifSo = false;
//
//        try (Connection conn = DatabaseManager.getConnection()) {
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            preparedStatement.setString(1, username);
//            ifSo = preparedStatement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return ifSo;
//    }

    public void saveHero(Hero hero, int userID) throws SQLException {
        String query = "INSERT INTO HEROES (class, level, maxHP, maxMana, attack, defense, userID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, hero.getHeroClassString());
            preparedStatement.setInt(2, hero.getLevel());
            preparedStatement.setInt(3, hero.getMaxHp());
            preparedStatement.setInt(4, hero.getMaxMana());
            preparedStatement.setInt(5, hero.getAttack());
            preparedStatement.setInt(6, hero.getDefense());
            preparedStatement.setInt(7, userID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Hero getHero(int userID) throws SQLException {
        String query = "SELECT * FROM HEROES WHERE userID = ?";

        try  (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return null; // do friday.
            }
        }
        return null;
    }

    public User login(String username, String password) throws SQLException {
        // String query = "SELECT idUSERS, username FROM USERS WHERE username = ? AND password = ?";
        String query = "SELECT idUSERS, username, password FROM USERS WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            // preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                //System.out.println("-=-=-=We got here.");
                String storedHash = resultSet.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(resultSet.getInt("idUSERS"), resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
