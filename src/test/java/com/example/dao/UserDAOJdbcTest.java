package com.example.dao;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.example.model.User;
import com.example.util.TestDatabaseConnectionUtil;

/**
 * Tests for the UserDAOJdbc class using H2 in-memory database.
 */
class UserDAOJdbcTest {

    private TestUserDAOJdbc userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        System.out.println("[UserDAOJdbcTest] Setting up test");
        userDAO = new TestUserDAOJdbc();
        
        // Explicitly make sure the database is initialized
        userDAO.ensureDatabaseInitialized();
        System.out.println("[UserDAOJdbcTest] Test setup complete");
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        // Reset the database for the next test
        TestDatabaseConnectionUtil.resetDatabase();
    }
    
    @Test
    void testInsertUser() throws SQLException {
        // Test data
        User user = new User("New User", "newuser@example.com", "Germany");
        
        // Call the method to test
        userDAO.insertUser(user);
        
        // Verify insertion by retrieving all users and checking the count
        List<User> users = userDAO.selectAllUsers();
        assertEquals(4, users.size(), "There should be 4 users after insertion");
        
        // Find the inserted user by email
        boolean userFound = users.stream()
            .anyMatch(u -> "newuser@example.com".equals(u.getEmail()));
        assertTrue(userFound, "The new user should be found in the database");
    }
    
    @Test
    void testSelectUser() throws SQLException {
        // Get user with ID 1 (should be John Doe based on our test data)
        User user = userDAO.selectUser(1);
        
        // Verify
        assertNotNull(user, "User with ID 1 should exist");
        assertEquals("John Doe", user.getName(), "User name should be John Doe");
        assertEquals("john@example.com", user.getEmail(), "User email should be john@example.com");
        assertEquals("USA", user.getCountry(), "User country should be USA");
    }
    
    @Test
    void testSelectAllUsers() throws SQLException {
        // Call the method to test
        List<User> users = userDAO.selectAllUsers();
        
        // Verify
        assertEquals(3, users.size(), "There should be 3 users in the database");
        
        // Check the first user
        User firstUser = users.get(0);
        assertEquals("John Doe", firstUser.getName());
        assertEquals("john@example.com", firstUser.getEmail());
        assertEquals("USA", firstUser.getCountry());
        
        // Check the second user
        User secondUser = users.get(1);
        assertEquals("Jane Smith", secondUser.getName());
        assertEquals("jane@example.com", secondUser.getEmail());
        assertEquals("Canada", secondUser.getCountry());
    }
    
    @Test
    void testUpdateUser() throws SQLException {
        // Retrieve user with ID 1
        User user = userDAO.selectUser(1);
        assertNotNull(user);
        
        // Update user details
        user.setName("John Doe Updated");
        user.setEmail("johndoe.updated@example.com");
        user.setCountry("UK");
        
        // Call the method to test
        boolean updated = userDAO.updateUser(user);
        
        // Verify the update was successful
        assertTrue(updated, "Update should return true");
        
        // Retrieve the updated user
        User updatedUser = userDAO.selectUser(1);
        
        // Verify
        assertEquals("John Doe Updated", updatedUser.getName(), "Name should be updated");
        assertEquals("johndoe.updated@example.com", updatedUser.getEmail(), "Email should be updated");
        assertEquals("UK", updatedUser.getCountry(), "Country should be updated");
    }
    
    @Test
    void testDeleteUser() throws SQLException {
        // Delete user with ID 1
        boolean deleted = userDAO.deleteUser(1);
        
        // Verify the deletion was successful
        assertTrue(deleted, "Delete should return true");
        
        // Try to retrieve the deleted user
        User deletedUser = userDAO.selectUser(1);
        
        // Verify
        assertNull(deletedUser, "User with ID 1 should no longer exist");
        
        // Check the number of users left
        List<User> users = userDAO.selectAllUsers();
        assertEquals(2, users.size(), "There should be 2 users left in the database");
    }
}
