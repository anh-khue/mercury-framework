package com.akframework.core.connection;

import com.akframework.kotlin.connection.ConnectionProperties;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements Serializable {

    private static final ConnectionProperties CONNECTION_PROPERTIES = ConnectionProperties.INSTANCE;

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(CONNECTION_PROPERTIES.getDriverClass());

        System.out.println(CONNECTION_PROPERTIES.getUrl());
        System.out.println(CONNECTION_PROPERTIES.getUsername());

        return DriverManager.getConnection(
                CONNECTION_PROPERTIES.getUrl(),
                CONNECTION_PROPERTIES.getUsername(),
                CONNECTION_PROPERTIES.getPassword()
        );
    }
}