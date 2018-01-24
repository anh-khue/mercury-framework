package io.osiris.connection.test;

import io.osiris.data.connection.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectionTest {

    @Test
    void openConnectionTest() throws SQLException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.openConnection();
        assertNotNull(connection);
    }
}
