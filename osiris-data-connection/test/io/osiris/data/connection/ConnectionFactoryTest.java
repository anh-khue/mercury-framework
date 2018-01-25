package io.osiris.data.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest {

    @Test
    void openConnection() {

        ConnectionFactory factory = new ConnectionFactory();
        try {
            Connection connection = factory.openConnection();
            assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}