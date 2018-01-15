package com.osiris.test.core.connection;

import com.osiris.core.connection.ConnectionManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class ConnectionManagerTest {

    @Test
    public void openConnection() {
        try {
            Connection connection = ConnectionManager.openConnection();

            assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}