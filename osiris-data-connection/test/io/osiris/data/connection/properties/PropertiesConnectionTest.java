package io.osiris.data.connection.properties;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesConnectionTest {

    @Test
    void openConnection() {
        try {
            Connection connection = PropertiesConnection.openConnection();

            assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}