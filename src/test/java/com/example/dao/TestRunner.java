package com.example.dao;

import org.junit.jupiter.api.Test;

/**
 * Simple test runner to help debug our test cases
 */
public class TestRunner {

    @Test
    public void runUserDAOJdbcTest() throws Exception {
        UserDAOJdbcTest test = new UserDAOJdbcTest();
        
        // Test 1: Select User
        test.setUp();
        try {
            test.testSelectUser();
        } finally {
            test.tearDown();
        }
        
        // Test 2: Select All Users
        test.setUp();
        try {
            test.testSelectAllUsers();
        } finally {
            test.tearDown();
        }
        
        // Test 3: Insert User
        test.setUp();
        try {
            test.testInsertUser();
        } finally {
            test.tearDown();
        }
        
        // Test 4: Update User
        test.setUp();
        try {
            test.testUpdateUser();
        } finally {
            test.tearDown();
        }
        
        // Test 5: Delete User
        test.setUp();
        try {
            test.testDeleteUser();
        } finally {
            test.tearDown();
        }
    }
}
