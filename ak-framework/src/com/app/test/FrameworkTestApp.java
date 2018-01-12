package com.app.test;

import com.akframework.core.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class FrameworkTestApp {

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionManager.openConnection();
            String message = connection != null ? "Success" : "Ahihi do ngoc";
            System.out.println(message);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
