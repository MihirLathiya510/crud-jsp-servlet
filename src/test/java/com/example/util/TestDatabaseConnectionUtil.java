package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test utility for creating an in-memory database connection for tests.
 */
public class TestDatabaseConnectionUtil {
    // Use a unique name that's shared across test instances with a file-based approach for persistence
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS PUBLIC";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static boolean initialized = false;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static synchronized void initializeDatabase() throws SQLException {
        if (initialized) {
            // Even if initialized, verify the table exists and has data
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = connection.createStatement()) {
                    
                java.sql.ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users");
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("[TestDatabaseConnectionUtil] Database already initialized with " + rs.getInt(1) + " rows");
                    return;
                }
                // If we get here, table exists but has no data - we'll reinitialize
                System.out.println("[TestDatabaseConnectionUtil] Database initialized but empty, reinitializing...");
                initialized = false;
            } catch (SQLException e) {
                // Table doesn't exist, we'll reinitialize
                System.out.println("[TestDatabaseConnectionUtil] Database not properly initialized, reinitializing...");
                initialized = false;
            }
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
             
            // Always recreate table for clean state
            statement.execute("DROP TABLE IF EXISTS users");
            
            // Create users table
            System.out.println("[TestDatabaseConnectionUtil] Creating users table");
            statement.execute("CREATE TABLE users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL, " +
                    "country VARCHAR(255) NOT NULL)");
            
            // Add test data
            System.out.println("[TestDatabaseConnectionUtil] Adding test data");
            statement.execute("INSERT INTO users (name, email, country) VALUES " +
                    "('John Doe', 'john@example.com', 'USA'), " +
                    "('Jane Smith', 'jane@example.com', 'Canada'), " +
                    "('Alice Johnson', 'alice@example.com', 'UK')");
            
            initialized = true;
            System.out.println("[TestDatabaseConnectionUtil] Database initialization complete");
        }
    }
    
    public static synchronized void resetDatabase() throws SQLException {
        System.out.println("[TestDatabaseConnectionUtil] Resetting database...");
        initialized = false;  // Reset the initialization flag
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            
            // Drop the existing tables
            System.out.println("[TestDatabaseConnectionUtil] Dropping existing tables...");
            statement.execute("DROP TABLE IF EXISTS users");
            
            // Reinitialize the database with a fresh schema and data
            System.out.println("[TestDatabaseConnectionUtil] Reinitializing database...");
            initializeDatabase();
        }
        
        System.out.println("[TestDatabaseConnectionUtil] Database reset complete");
    }
}
