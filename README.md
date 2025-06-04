<!-- filepath: /Users/mihir/work/crud-jsp-servlet/README.md.new -->
# JSP-Servlet CRUD Application with Hibernate Integration

A full-stack CRUD (Create, Read, Update, Delete) web application built using JSP, Servlets, and PostgreSQL with Docker containerization and dual implementation of data access logic (JDBC and Hibernate ORM).

## Features

- Complete User management with CRUD operations
- PostgreSQL database integration
- Containerized with Docker and Docker Compose for easy deployment
- Responsive UI with clean interface
- Multiple DAO implementations (JDBC and Hibernate) that can be switched at runtime
- DAO Factory pattern implementation for interchangeable data access strategies
- JPA Entity annotations for ORM mapping

## Technologies Used

- Java Servlets & JSP
- PostgreSQL Database
- Hibernate ORM/JPA
- Docker & Docker Compose
- Maven
- HTML/CSS

## Project Structure

```
├── src/main/
│   ├── java/                       # Java source code
│   │   └── com/example/
│   │       ├── dao/                # Data Access Objects
│   │       │   ├── DAOFactory.java # Factory for creating DAOs
│   │       │   ├── UserDAO.java    # DAO Interface
│   │       │   ├── UserDAOJdbc.java # JDBC implementation
│   │       │   └── UserDAOHibernate.java # Hibernate implementation
│   │       ├── model/              # Model classes with JPA annotations
│   │       │   └── User.java       # User entity
│   │       ├── util/               # Utility classes
│   │       │   ├── DatabaseConnectionUtil.java # JDBC connection utility
│   │       │   ├── HibernateUtil.java # Hibernate session factory utility
│   │       │   └── HibernateListener.java # ServletContextListener for Hibernate
│   │       └── web/                # Servlets
│   │           └── UserServlet.java # Controller servlet for handling requests
│   ├── resources/                  # Configuration and resources
│   │   ├── database.properties     # Database connection properties
│   │   ├── hibernate.cfg.xml       # Hibernate configuration
│   │   └── sql/
│   │       └── init.sql            # Database initialization script
│   └── webapp/                     # Web resources
│       ├── index.jsp               # Home page
│       ├── user-list.jsp           # User listing page
│       ├── user-form.jsp           # Add/Edit user form
│       ├── error.jsp               # Error page
│       └── WEB-INF/
│           └── web.xml             # Web application configuration
├── docker-compose.yml              # Docker Compose configuration
├── Dockerfile                      # Tomcat container configuration
└── pom.xml                         # Maven project configuration
```

## Prerequisites

- JDK 11 or higher
- Maven
- Docker and Docker Compose

## Getting Started

### Running with Docker (recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/crud-jsp-servlet.git
   cd crud-jsp-servlet
   ```

2. Build the application:
   ```bash
   mvn clean package
   ```

3. Start the containers:
   ```bash
   docker-compose up -d
   ```

4. Access the application:
   Open [http://localhost:8081/crud-jsp-servlet](http://localhost:8081/crud-jsp-servlet) in your browser

### Accessing PostgreSQL Database

You can connect to the PostgreSQL database using any client with these details:
- Host: localhost
- Port: 5433
- Database: crudapp
- Username: postgres
- Password: postgres

## Development

### Rebuilding After Changes

When you make changes to the code, rebuild and redeploy with:
```bash
mvn clean package && docker cp target/crud-jsp-servlet.war crud-jsp-servlet-tomcat-1:/usr/local/tomcat/webapps/
```

### Checking Logs

```bash
docker logs -f crud-jsp-servlet-tomcat-1
```

### Testing Different DAO Implementations

1. Edit the `src/main/webapp/WEB-INF/web.xml` file to change the DAO implementation:
   ```xml
   <context-param>
     <param-name>dao.implementation</param-name>
     <param-value>jdbc</param-value> <!-- Change to hibernate or jdbc -->
   </context-param>
   ```

2. Rebuild and redeploy the application:
   ```bash
   mvn clean package && docker cp target/crud-jsp-servlet.war crud-jsp-servlet-tomcat-1:/usr/local/tomcat/webapps/
   ```

3. Check the logs to confirm the active implementation:
   ```bash
   docker logs crud-jsp-servlet-tomcat-1 | grep "Using.*DAO Implementation"
   ```

## Switching Between DAO Implementations

The application supports two DAO implementations:

1. **JDBC** - Standard JDBC implementation
2. **Hibernate** - JPA/Hibernate ORM implementation

You can easily switch between implementations by modifying the `web.xml` file:

```xml
<!-- Set System property to choose DAO implementation -->
<context-param>
  <param-name>dao.implementation</param-name>
  <param-value>jdbc</param-value> <!-- Options: jdbc, hibernate -->
</context-param>
```

Change the `param-value` to either `jdbc` or `hibernate` to switch between implementations.

## DAO Implementation Details

### JDBC Implementation

The JDBC implementation (`UserDAOJdbc.java`) provides:
- Direct SQL execution
- Manual connection management
- Explicit SQL queries for all CRUD operations
- Lightweight approach suitable for simple applications

### Hibernate Implementation

The Hibernate implementation (`UserDAOHibernate.java`) offers:
- Object-relational mapping with JPA annotations
- Connection pooling
- Caching capabilities
- Transaction management
- Reduced boilerplate SQL code

Both implementations follow the same interface (`UserDAO.java`) and can be switched without affecting the rest of the application thanks to the DAO Factory pattern.

## Architecture and Design Patterns

The application incorporates several design patterns and architectural principles:

### 1. DAO (Data Access Object) Pattern
- Separates data access logic from business logic
- Defines a unified interface (`UserDAO`) for different implementations
- Makes the application more maintainable and testable

### 2. Factory Pattern
- `DAOFactory` provides a way to get the appropriate DAO implementation
- Encapsulates the creation logic and dependency injection
- Allows runtime switching between implementations

### 3. MVC (Model-View-Controller) Pattern
- **Model**: `User` class and DAOs
- **View**: JSP pages (`user-list.jsp`, `user-form.jsp`, etc.)
- **Controller**: `UserServlet` handling HTTP requests and responses

### 4. Singleton Pattern
- `HibernateUtil` implements a singleton pattern for `SessionFactory`
- `DatabaseConnectionUtil` manages a single connection pool for JDBC

### 5. Observer Pattern
- `HibernateListener` observes the web application lifecycle events
- Initializes and cleans up Hibernate resources based on application events

## Known Limitations and Future Improvements

- Add user authentication and authorization
- Implement more extensive input validation
- Add unit and integration tests for both DAO implementations
- Add transaction handling for complex operations
- Improve error handling and user feedback
- Implement pagination for large datasets
- Add sorting and filtering functionality

## Conclusion

This project demonstrates a well-structured web application with interchangeable data access strategies using the DAO pattern and Factory design pattern. By providing both JDBC and Hibernate implementations, it showcases the flexibility of properly designed applications and allows for comparing the two approaches in a real-world application.
