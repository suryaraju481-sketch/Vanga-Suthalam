package com.vangasuthalam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/vanga_suthalam_db"
            + "?useSSL=false"
            + "&serverTimezone=Asia/Kolkata"
            + "&allowPublicKeyRetrieval=true";

    private static final String USER = "root";

    // PUT YOUR EXISTING MYSQL PASSWORD HERE

    private static final String PASSWORD = System.getenv("VANGA_DB_PASSWORD");
   

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found. Check mysql-connector JAR.",
                    e
            );
        }

        Connection con = DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );

        System.out.println("Database connection successfully.");

        return con;
    }
}