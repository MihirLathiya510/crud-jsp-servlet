package com.example.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

/**
 * Simple test to just verify H2 connection is working
 */
public class SimpleH2Test {
    
    @Test
    public void testH2Connection() throws SQLException {
        TestUserDAOJdbc userDAO = new TestUserDAOJdbc();
        assertNotNull(userDAO);
        
        // Just try to get the connection
        assertDoesNotThrow(() -> {
            userDAO.selectAllUsers();
        });
    }
}
