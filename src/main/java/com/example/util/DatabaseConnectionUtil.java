package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {
    private static final String DB_URL = System.getenv("DB_URL") != null ? 
                                         System.getenv("DB_URL") : "jdbc:postgresql://db:5432/crudapp";
    private static final String DB_USER = System.getenv("DB_USER") != null ? 
                                         System.getenv("DB_USER") : "postgres";
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD") != null ? 
                                             System.getenv("DB_PASSWORD") : "postgres";
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
