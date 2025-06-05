package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.example.model.User;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from configuration
            Configuration configuration = new Configuration();
            
            // Try to load properties from file
            try {
                Properties settings = new Properties();
                InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("database.properties");
                if (input != null) {
                    settings.load(input);
                    input.close();
                    
                    // Apply properties from file
                    configuration.setProperty(Environment.DRIVER, settings.getProperty("jdbc.driver"));
                    configuration.setProperty(Environment.URL, settings.getProperty("jdbc.url"));
                    configuration.setProperty(Environment.USER, settings.getProperty("jdbc.username"));
                    configuration.setProperty(Environment.PASS, settings.getProperty("jdbc.password"));
                    configuration.setProperty(Environment.DIALECT, settings.getProperty("hibernate.dialect"));
                    configuration.setProperty(Environment.SHOW_SQL, settings.getProperty("hibernate.show_sql"));
                    configuration.setProperty(Environment.FORMAT_SQL, settings.getProperty("hibernate.format_sql"));
                    configuration.setProperty(Environment.HBM2DDL_AUTO, settings.getProperty("hibernate.hbm2ddl.auto"));
                    configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                    
                    System.out.println("Loaded database properties from file");
                } else {
                    // Fall back to hibernate.cfg.xml
                    configuration.configure("hibernate.cfg.xml");
                    System.out.println("Loaded hibernate configuration from hibernate.cfg.xml");
                }
            } catch (IOException e) {
                // Fall back to hibernate.cfg.xml
                configuration.configure("hibernate.cfg.xml");
                System.out.println("Failed to load properties file, using hibernate.cfg.xml: " + e.getMessage());
            }
            
            // Add annotated classes
            configuration.addAnnotatedClass(User.class);
            
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                    
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
