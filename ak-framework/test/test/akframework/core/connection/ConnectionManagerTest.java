package test.akframework.core.connection;

import com.akframework.core.connection.ConnectionManager;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerTest {

    @Test
    public void openConnection() {
        try {
            Connection connection = ConnectionManager.openConnection();

            Assert.assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}