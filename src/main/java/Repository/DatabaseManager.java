package Repository;

import GlobalVariables.ApplicationConstants;

import java.sql.*;

public class DatabaseManager {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                ApplicationConstants.db_link,
                ApplicationConstants.db_user,
                ApplicationConstants.db_password
        );
    }
}
