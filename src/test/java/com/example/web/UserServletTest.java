package com.example.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import com.example.dao.UserDAO;
import com.example.model.User;

class UserServletTest {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private UserDAO userDAO;
    private UserServlet servlet;
    
    @BeforeEach
    void setUp() throws Exception {
        // Initialize mocks
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        userDAO = mock(UserDAO.class);
        
        // Create the servlet and inject the mocked DAO
        servlet = new UserServlet();
        
        // Use reflection to inject our mock DAO into the servlet
        java.lang.reflect.Field daoField = UserServlet.class.getDeclaredField("userDAO");
        daoField.setAccessible(true);
        daoField.set(servlet, userDAO);
    }
    
    @Test
    void testListUsers() throws Exception {
        // Prepare test data
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "john@example.com", "USA"));
        users.add(new User(2L, "Jane", "jane@example.com", "Canada"));
        
        // Mock behavior of dependencies
        when(request.getServletPath()).thenReturn("/list");
        when(userDAO.selectAllUsers()).thenReturn(users);
        when(request.getRequestDispatcher("user-list.jsp")).thenReturn(dispatcher);
        
        // Call the method to test
        servlet.doGet(request, response);
        
        // Verify the expected interactions
        verify(request).setAttribute("listUser", users);
        verify(request).getRequestDispatcher("user-list.jsp");
        verify(dispatcher).forward(request, response);
    }
    
    @Test
    void testShowNewForm() throws Exception {
        // Mock behavior
        when(request.getServletPath()).thenReturn("/new");
        when(request.getRequestDispatcher("user-form.jsp")).thenReturn(dispatcher);
        
        // Call the method to test
        servlet.doGet(request, response);
        
        // Verify the expected interactions
        verify(request).getRequestDispatcher("user-form.jsp");
        verify(dispatcher).forward(request, response);
    }
    
    @Test
    void testShowEditForm() throws Exception {
        // Set up the request parameter
        when(request.getServletPath()).thenReturn("/edit");
        when(request.getParameter("id")).thenReturn("1");
        
        // Mock the user that will be returned
        User existingUser = new User(1L, "John", "john@example.com", "USA");
        when(userDAO.selectUser(1L)).thenReturn(existingUser);
        when(request.getRequestDispatcher("user-form.jsp")).thenReturn(dispatcher);
        
        // Call the method to test
        servlet.doGet(request, response);
        
        // Verify interactions
        verify(request).setAttribute("user", existingUser);
        verify(request).getRequestDispatcher("user-form.jsp");
        verify(dispatcher).forward(request, response);
    }
    
    @Test
    void testInsertUser() throws Exception {
        // Set up the request parameters
        when(request.getServletPath()).thenReturn("/insert");
        when(request.getParameter("name")).thenReturn("New User");
        when(request.getParameter("email")).thenReturn("new@example.com");
        when(request.getParameter("country")).thenReturn("Australia");
        
        // Call the method to test
        servlet.doGet(request, response);
        
        // Verify the DAO was called with the correct arguments
        // We need to use a custom ArgumentMatcher since User doesn't override equals()
        verify(userDAO).insertUser(org.mockito.ArgumentMatchers.argThat(user -> 
            user.getName().equals("New User") &&
            user.getEmail().equals("new@example.com") &&
            user.getCountry().equals("Australia")
        ));
        verify(response).sendRedirect("list");
    }
    
    @Test
    void testUpdateUser() throws Exception {
        // Set up the request parameters
        when(request.getServletPath()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Updated User");
        when(request.getParameter("email")).thenReturn("updated@example.com");
        when(request.getParameter("country")).thenReturn("UK");
        
        // Mock the updateUser() method to return true (success)
        when(userDAO.updateUser(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(true);
        
        // Call the method to test
        servlet.doGet(request, response);
        
        // Verify the DAO was called with the correct arguments
        verify(userDAO).updateUser(org.mockito.ArgumentMatchers.argThat(user -> 
            user.getId() == 1L &&
            user.getName().equals("Updated User") &&
            user.getEmail().equals("updated@example.com") &&
            user.getCountry().equals("UK")
        ));
        verify(response).sendRedirect("list");
    }
    
    @Test
    void testDeleteUser() throws Exception {
        // Set up the request parameter
        when(request.getServletPath()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("1");
        
        // Mock the deleteUser() method to return true (success)
        when(userDAO.deleteUser(1L)).thenReturn(true);
        
        // Call the method to test
        servlet.doGet(request, response);
        
        // Verify the DAO was called with the correct id
        verify(userDAO).deleteUser(1L);
        verify(response).sendRedirect("list");
    }
    
    @Test
    void testHandleSQLException() throws SQLException, IOException {
        // Set up the scenario to throw SQLException
        when(request.getServletPath()).thenReturn("/list");
        when(userDAO.selectAllUsers()).thenThrow(new SQLException("Database error"));
        
        // Use assertThrows for cleaner exception testing
        ServletException exception = assertThrows(
            ServletException.class,
            () -> servlet.doGet(request, response),
            "Expected servlet to throw ServletException"
        );
        
        // Verify the cause is the expected SQLException
        assertEquals("java.sql.SQLException: Database error", exception.getCause().toString());
    }
}
