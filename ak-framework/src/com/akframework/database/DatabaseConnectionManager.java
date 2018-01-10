package com.akframework.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.*;
import java.util.Optional;

public class DatabaseConnectionManager implements Serializable {

    public static Connection openConnection() throws SQLException, ClassNotFoundException {

        ConnectionProperties connectionProperties = ConnectionProvider.retrieveProperties()
                .orElseThrow(SQLException::new);

        Class.forName(connectionProperties.driverClass);

        return DriverManager.getConnection(
                connectionProperties.url,
                connectionProperties.username,
                connectionProperties.password
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


class ConnectionProperties {

    protected final String driverClass;
    protected final String url;
    protected final String username;
    protected final String password;

    ConnectionProperties(String driverClass, String url, String username, String password) {
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }
}


class ConnectionProvider {

    protected static Optional<ConnectionProperties> retrieveProperties() {

        return Optional.ofNullable(ConnectionConsumer.getProperties());
    }


    private static class ConnectionConsumer {

        private static ConnectionProperties properties;

        protected static ConnectionProperties getProperties() {
            try {
                consumeProperties();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return properties;
        }

        private static void consumeProperties() throws FileNotFoundException {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Optional<URL> url = Optional.ofNullable(classLoader.getResource("resources/connection.xml"));

            File connectionResource = new File(url.map(URL::getFile)
                    .orElseThrow(FileNotFoundException::new));

//        System.out.println(config.getAbsolutePath());

            try {
                Document document = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(connectionResource);
                document.getDocumentElement().normalize();

                System.out.println("Root: " + document.getDocumentElement().getNodeName());
                Node node = document.getElementsByTagName("connection").item(0);

                readNode(node);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }

        }

        private static void readNode(Node node) {

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String driverClass = element.getElementsByTagName("driver").item(0).getTextContent();
                System.out.println(driverClass);
                String url = element.getElementsByTagName("url").item(0).getTextContent();
                System.out.println(url);
                String username = element.getElementsByTagName("username").item(0).getTextContent();
                System.out.println(username);
                String password = element.getElementsByTagName("password").item(0).getTextContent();
                System.out.println(password);

                properties = new ConnectionProperties(driverClass, url, username, password);
            }
        }
    }

}
