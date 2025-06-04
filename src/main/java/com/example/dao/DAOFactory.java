package com.example.dao;

import javax.servlet.ServletContext;

public class DAOFactory {
    
    // Default to hibernate if not specified
    private static String daoImplementation = "hibernate";
    private static UserDAO userDAO;
    
    public static void setDaoImplementation(String implementation) {
        daoImplementation = implementation;
        // Reset the DAO so it will be recreated with the new implementation
        userDAO = null;
    }
    
    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            if ("jdbc".equalsIgnoreCase(daoImplementation)) {
                userDAO = new UserDAOJdbc();
                System.out.println("Using JDBC DAO Implementation");
            } else {
                userDAO = new UserDAOHibernate();
                System.out.println("Using Hibernate DAO Implementation");
            }
        }
        return userDAO;
    }
    
    public static void configureDaoImplementation(ServletContext context) {
        String daoImpl = context.getInitParameter("dao.implementation");
        if (daoImpl != null && !daoImpl.isEmpty()) {
            setDaoImplementation(daoImpl);
        }
    }
}
