package com.example.dao;

import com.example.model.User;
import com.example.util.TestHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * A test implementation of UserDAOHibernate that uses H2 in-memory database.
 */
public class TestUserDAOHibernate extends UserDAOHibernate {
    
    @Override
    protected SessionFactory getSessionFactory() {
        return TestHibernateUtil.getSessionFactory();
    }
}
