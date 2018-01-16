package com.osiris.data.connection.xml;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlConnectionFactoryTest {

    @Test
    void openConnection() {
        try {
            XmlConnectionFactory connectionFactory = new XmlConnectionFactory();
            Connection connection = connectionFactory.openConnection();

            assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}