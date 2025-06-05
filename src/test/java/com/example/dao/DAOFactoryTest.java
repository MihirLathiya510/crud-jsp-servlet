package com.example.dao;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DAOFactoryTest {
    
    @Test
    void testGetUserDAOWithJdbc() {
        // Set the implementation to JDBC and verify we get the right implementation
        DAOFactory.setDaoImplementation("jdbc");
        UserDAO dao = DAOFactory.getUserDAO();
        assertTrue(dao instanceof UserDAOJdbc);
    }
    
    @Test
    void testGetUserDAOWithHibernate() {
        // Set the implementation to Hibernate and verify we get the right implementation
        DAOFactory.setDaoImplementation("hibernate");
        UserDAO dao = DAOFactory.getUserDAO();
        assertTrue(dao instanceof UserDAOHibernate);
    }
    
    @Test
    void testGetUserDAOWithInvalidImplementation() {
        // If we provide an invalid implementation, it should default to Hibernate
        DAOFactory.setDaoImplementation("invalid");
        UserDAO dao = DAOFactory.getUserDAO();
        // The current implementation defaults to Hibernate for unknown values
        assertTrue(dao instanceof UserDAOHibernate);
    }
    
    @Test
    void testConfigureDaoImplementation() {
        // Create a mock ServletContext
        ServletContext mockContext = Mockito.mock(ServletContext.class);
        // Configure the mock to return "jdbc" for the dao.implementation parameter
        Mockito.when(mockContext.getInitParameter("dao.implementation")).thenReturn("jdbc");
        
        // Call the method under test
        DAOFactory.configureDaoImplementation(mockContext);
        
        // Verify that the implementation is now JDBC
        UserDAO dao = DAOFactory.getUserDAO();
        assertTrue(dao instanceof UserDAOJdbc);
    }
}
