package com.example.dao;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import com.example.model.User;
import com.example.util.TestHibernateUtil;

/**
 * Tests for the UserDAOHibernate class using H2 in-memory database.
 */
class UserDAOHibernateTest {

    private TestUserDAOHibernate userDAO;
    
    @BeforeAll
    static void setUpClass() {
        // Initialize the SessionFactory
        TestHibernateUtil.getSessionFactory();
    }
    
    @AfterAll
    static void tearDownClass() {
        // Shutdown the SessionFactory
        TestHibernateUtil.shutdown();
    }

    @BeforeEach
    void setUp() {
        // Create a new test DAO for each test
        userDAO = new TestUserDAOHibernate();
    }
    
    @Test
    void testInsertUser() throws SQLException {
        // Test data
        User user = new User("New User", "newuser@example.com", "Germany");
        
        // Call the method to test
        userDAO.insertUser(user);
        
        // Check that the user exists in the database
        assertNotNull(user.getId(), "User should have an ID after being inserted");
        
        // Verify insertion by retrieving the user
        User insertedUser = userDAO.selectUser(user.getId());
        assertNotNull(insertedUser, "User should exist in the database");
        assertEquals("New User", insertedUser.getName(), "User name should match");
        assertEquals("newuser@example.com", insertedUser.getEmail(), "User email should match");
        assertEquals("Germany", insertedUser.getCountry(), "User country should match");
    }
    
    @Test
    void testSelectUser() throws SQLException {
        // Create a user to select
        User newUser = new User("Test User", "test@example.com", "USA");
        userDAO.insertUser(newUser);
        long userId = newUser.getId();
        
        // Call the method to test
        User selectedUser = userDAO.selectUser(userId);
        
        // Verify
        assertNotNull(selectedUser, "User should exist");
        assertEquals(userId, selectedUser.getId(), "User ID should match");
        assertEquals("Test User", selectedUser.getName(), "User name should match");
        assertEquals("test@example.com", selectedUser.getEmail(), "User email should match");
        assertEquals("USA", selectedUser.getCountry(), "User country should match");
    }
    
    @Test
    void testSelectAllUsers() throws SQLException {
        // Initial database should have 3 users from TestHibernateUtil
        List<User> initialUsers = userDAO.selectAllUsers();
        int initialCount = initialUsers.size();
        
        // Add more test users
        User user1 = new User("Test User 1", "test1@example.com", "France");
        User user2 = new User("Test User 2", "test2@example.com", "Spain");
        userDAO.insertUser(user1);
        userDAO.insertUser(user2);
        
        // Call the method to test
        List<User> users = userDAO.selectAllUsers();
        
        // Verify
        assertEquals(initialCount + 2, users.size(), "There should be " + (initialCount + 2) + " users");
        
        // Check that our new users are in the list
        boolean foundUser1 = false;
        boolean foundUser2 = false;
        
        for (User user : users) {
            if (user.getId().equals(user1.getId())) {
                foundUser1 = true;
                assertEquals("Test User 1", user.getName());
                assertEquals("test1@example.com", user.getEmail());
                assertEquals("France", user.getCountry());
            } else if (user.getId().equals(user2.getId())) {
                foundUser2 = true;
                assertEquals("Test User 2", user.getName());
                assertEquals("test2@example.com", user.getEmail());
                assertEquals("Spain", user.getCountry());
            }
        }
        
        assertTrue(foundUser1, "User 1 should be in the list");
        assertTrue(foundUser2, "User 2 should be in the list");
    }
    
    @Test
    void testUpdateUser() throws SQLException {
        // Create a user to update
        User newUser = new User("User to Update", "update@example.com", "Italy");
        userDAO.insertUser(newUser);
        long userId = newUser.getId();
        
        // Update user details
        newUser.setName("Updated Name");
        newUser.setEmail("updated@example.com");
        newUser.setCountry("Updated Country");
        
        // Call the method to test
        boolean result = userDAO.updateUser(newUser);
        
        // Verify
        assertTrue(result, "Update should return true");
        
        // Retrieve the updated user
        User updatedUser = userDAO.selectUser(userId);
        assertNotNull(updatedUser, "User should exist");
        assertEquals("Updated Name", updatedUser.getName(), "Name should be updated");
        assertEquals("updated@example.com", updatedUser.getEmail(), "Email should be updated");
        assertEquals("Updated Country", updatedUser.getCountry(), "Country should be updated");
    }
    
    @Test
    void testDeleteUser() throws SQLException {
        // Create a user to delete
        User newUser = new User("User to Delete", "delete@example.com", "Brazil");
        userDAO.insertUser(newUser);
        long userId = newUser.getId();
        
        // Verify user exists
        assertNotNull(userDAO.selectUser(userId), "User should exist before deletion");
        
        // Call the method to test
        boolean result = userDAO.deleteUser(userId);
        
        // Verify
        assertTrue(result, "Delete should return true");
        
        // Check the user has been deleted
        User deletedUser = userDAO.selectUser(userId);
        assertNull(deletedUser, "User should no longer exist");
    }
    
    @Test
    void testDeleteNonExistentUser() throws SQLException {
        // Call the method with an ID that doesn't exist
        boolean result = userDAO.deleteUser(99999L);
        
        // Verify
        assertFalse(result, "Delete should return false for non-existent user");
    }
}
