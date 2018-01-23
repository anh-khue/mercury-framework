package io.osiris.data.connection;

import io.osiris.data.connection.properties.PropertiesConnection;
import io.osiris.data.connection.xml.XmlConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory implements ConnectionProvider {

    @Override
    public Connection openConnection() throws ClassNotFoundException, SQLException {
        boolean propertiesFileExist = new File("resources", "connection.properties").exists();

        if (propertiesFileExist) return PropertiesConnection.openConnection();

        return XmlConnection.openConnection();
    }
}
