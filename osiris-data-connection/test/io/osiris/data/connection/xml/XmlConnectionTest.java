package io.osiris.data.connection.xml;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlConnectionTest {

    @Test
    void openConnection() {
        try {
            XmlConnection connectionFactory = new XmlConnection();
            Connection connection = connectionFactory.openConnection();

            assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}