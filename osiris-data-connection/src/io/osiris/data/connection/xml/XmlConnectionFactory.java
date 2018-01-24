package io.osiris.data.connection.xml;

import io.osiris.data.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class XmlConnectionFactory implements ConnectionFactory {

    private final ConnectionProperties CONNECTION_PROPERTIES = ConnectionProperties.INSTANCE;

    public Connection openConnection() throws ClassNotFoundException, SQLException {

        Class.forName(CONNECTION_PROPERTIES.getDriverClass());

        return DriverManager.getConnection(
                CONNECTION_PROPERTIES.getUrl(),
                CONNECTION_PROPERTIES.getUsername(),
                CONNECTION_PROPERTIES.getPassword()
        );
    }
}
