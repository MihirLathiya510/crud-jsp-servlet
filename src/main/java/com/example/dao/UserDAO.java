package com.example.dao;

import com.example.model.User;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object Interface for User entity
 */
public interface UserDAO {
    void insertUser(User user) throws SQLException;
    User selectUser(long id) throws SQLException;
    List<User> selectAllUsers() throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean deleteUser(long id) throws SQLException;
}
