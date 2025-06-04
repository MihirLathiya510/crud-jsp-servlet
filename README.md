# JSP-Servlet CRUD Application

A simple CRUD (Create, Read, Update, Delete) web application built using JSP, Servlets, and PostgreSQL.

## Features

- User management with CRUD operations
- PostgreSQL database integration
- Containerized with Docker and Docker Compose
- Responsive UI

## Technologies Used

- Java Servlets & JSP
- PostgreSQL Database
- Docker & Docker Compose
- Maven
- HTML/CSS

## Project Structure

```
├── src/main/
│   ├── java/               # Java source code
│   │   └── com/example/
│   │       ├── dao/        # Data Access Objects
│   │       ├── model/      # Model classes
│   │       ├── util/       # Utility classes
│   │       └── web/        # Servlets
│   ├── resources/          # Resources like SQL scripts
│   └── webapp/             # Web resources (JSP, CSS, JS)
├── docker-compose.yml      # Docker Compose configuration
├── Dockerfile              # Tomcat container configuration
└── pom.xml                 # Maven project configuration
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
   Open [http://localhost:8081](http://localhost:8081) in your browser

### Accessing PostgreSQL Database

You can connect to the PostgreSQL database using any client with these details:
- Host: localhost
- Port: 5433
- Database: crudapp
- Username: postgres
- Password: postgres

## Development

### Rebuilding After Changes

```bash
mvn clean package && docker-compose restart tomcat
```

### Checking Logs

```bash
docker-compose logs -f tomcat
```
