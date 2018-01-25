package framework.util;

import framework.config.ConnectionConfig;
import framework.config.ConnectionInfor;

import java.io.Serializable;
import java.sql.*;

public class DbUtils implements Serializable {
    public static Connection makeConnection() throws SQLException, ClassNotFoundException {
        ConnectionInfor connectionInfor = ConnectionConfig.getConnectionInfor();
        Class.forName(connectionInfor.getDriverClass());
        return DriverManager.getConnection(
                connectionInfor.getUrl(),
                connectionInfor.getUsername(),
                connectionInfor.getPassword()
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
