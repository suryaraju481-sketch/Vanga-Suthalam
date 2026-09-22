package com.vangasuthalam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found. Check mysql-connector JAR.",
                    e
            );
        }

        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String database = System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        String url =
                "jdbc:mysql://" + host + ":" + port + "/" + database
                +  "?sslMode=REQUIRED"
                + "&serverTimezone=Asia/Kolkata"
                + "&allowPublicKeyRetrieval=true";

        Connection con = DriverManager.getConnection(
                url,
                user,
                password
        );

        System.out.println("Database connection successfully.");

        return con;
    }
}