package com.example.util;

import com.example.dao.DAOFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application lifecycle listener for initializing and shutting down Hibernate
 */
@WebListener
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Configure DAO Implementation based on context parameters
        DAOFactory.configureDaoImplementation(sce.getServletContext());
        
        // Initialize Hibernate when web application starts
        HibernateUtil.getSessionFactory();
        System.out.println("Hibernate SessionFactory initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close Hibernate SessionFactory when web application shuts down
        HibernateUtil.shutdown();
        System.out.println("Hibernate SessionFactory closed successfully.");
    }
}
