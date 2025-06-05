package com.example.dao;

import com.example.util.TestDatabaseConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A test implementation of UserDAOJdbc that uses an H2 in-memory database.
 */
public class TestUserDAOJdbc extends UserDAOJdbc {
    
    /**
     * Override the method that gets the connection to use our test database
     */
    @Override
    protected Connection getConnection() throws SQLException {
        System.out.println("[TestUserDAOJdbc] Getting test database connection");
        Connection conn = TestDatabaseConnectionUtil.getConnection();
        System.out.println("[TestUserDAOJdbc] Got connection: " + (conn != null ? "valid" : "null"));
        return conn;
    }
    
    /**
     * Ensure that the database is initialized with tables and test data
     */
    public void ensureDatabaseInitialized() throws SQLException {
        System.out.println("[TestUserDAOJdbc] Ensuring database is initialized");
        
        // First ensure the database structure exists
        TestDatabaseConnectionUtil.initializeDatabase();
        
        // Then verify we can access it
        try (Connection conn = getConnection()) {
            try (java.sql.Statement stmt = conn.createStatement()) {
                // Check if the table exists and has data
                java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("[TestUserDAOJdbc] Database check successful, found " + count + " users");
                }
            } catch (SQLException e) {
                System.out.println("[TestUserDAOJdbc] Database check failed: " + e.getMessage());
                // If verification fails, try to recreate the schema
                TestDatabaseConnectionUtil.resetDatabase();
                throw new SQLException("Database verification failed, reset attempted", e);
            }
        }
        
        System.out.println("[TestUserDAOJdbc] Database initialization complete");
    }
}
