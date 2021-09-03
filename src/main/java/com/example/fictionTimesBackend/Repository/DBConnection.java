package com.example.fictionTimesBackend.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        if (connection == null) {
            // Load the properties from application properties
            Properties properties = new Properties();
            properties.load(DBConnection.class.getResourceAsStream("/database.properties"));
            String driverClass = properties.getProperty("db.driver.class");
            String dbUrl = properties.getProperty("db.conn.url");
            String userName = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Set up the connection
            Class.forName(driverClass);
            connection = DriverManager.getConnection(dbUrl, userName, password);
            return connection;
        }
        return connection;
    }
}
