package app.util;

import java.io.Serializable;
import java.sql.*;

public class DbUtils implements Serializable {
    public static Connection makeConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=bmag_db;instanceName=SQL16ENTERPRISE",
                "sa",
                "123456"
        );
    }

    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection connection, PreparedStatement statement) {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
