package com.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.example.model.User;

/**
 * Test version of HibernateUtil that uses H2 in-memory database
 */
public class TestHibernateUtil {
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                
                // H2 in-memory database settings - use the same config as TestDatabaseConnectionUtil
                configuration.setProperty(Environment.DRIVER, "org.h2.Driver");
                configuration.setProperty(Environment.URL, "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS PUBLIC");
                configuration.setProperty(Environment.USER, "sa");
                configuration.setProperty(Environment.PASS, "");
                configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
                configuration.setProperty(Environment.SHOW_SQL, "true");
                configuration.setProperty(Environment.FORMAT_SQL, "true");
                configuration.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
                configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                
                // Add annotated classes
                configuration.addAnnotatedClass(User.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                
                // Initialize with some test data
                initializeTestData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    
    private static void initializeTestData() {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            
            // Add some test users
            User user1 = new User("John Doe", "john@example.com", "USA");
            User user2 = new User("Jane Smith", "jane@example.com", "Canada");
            User user3 = new User("Alice Johnson", "alice@example.com", "UK");
            
            session.save(user1);
            session.save(user2);
            session.save(user3);
            
            session.getTransaction().commit();
        }
    }
    
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
