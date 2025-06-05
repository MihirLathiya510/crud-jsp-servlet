package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class UserTest {
    
    @Test
    void testEmptyConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getCountry());
    }
    
    @Test
    void testConstructorWithoutId() {
        User user = new User("John", "john@example.com", "USA");
        assertNull(user.getId());
        assertEquals("John", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("USA", user.getCountry());
    }
    
    @Test
    void testFullConstructor() {
        User user = new User(1L, "Jane", "jane@example.com", "Canada");
        assertEquals(1L, user.getId());
        assertEquals("Jane", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("Canada", user.getCountry());
    }
    
    @Test
    void testSetters() {
        User user = new User();
        
        user.setId(2L);
        assertEquals(2L, user.getId());
        
        user.setName("Alice");
        assertEquals("Alice", user.getName());
        
        user.setEmail("alice@example.com");
        assertEquals("alice@example.com", user.getEmail());
        
        user.setCountry("Australia");
        assertEquals("Australia", user.getCountry());
    }
}
