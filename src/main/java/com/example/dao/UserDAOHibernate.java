package com.example.dao;

import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDAOHibernate implements UserDAO {

    /**
     * Get the session factory - can be overridden in tests
     */
    protected SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }
    
    @Override
    public void insertUser(User user) throws SQLException {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error inserting user", e);
        }
    }

    @Override
    public User selectUser(long id) throws SQLException {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new SQLException("Error selecting user with id: " + id, e);
        }
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new SQLException("Error selecting all users", e);
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error updating user", e);
        }
    }

    @Override
    public boolean deleteUser(long id) throws SQLException {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error deleting user with id: " + id, e);
        }
    }
}
