# Health Check API

A RESTful API service for monitoring application and database connectivity.

## Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.7
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **ORM**: Hibernate (Spring Data JPA)
- **Additional Libraries**: Lombok

## Prerequisites

- Java JDK 21 or higher
- MySQL 8.0 or higher
- Maven 3.6+

## Database Setup

1. Install MySQL 8.0+
2. Create the database:
```sql
CREATE DATABASE healthcheck_db;
```

The application will automatically create the required tables on startup.

## Configuration

Set environment variables or update `src/main/resources/application.properties`:
```properties
server.port=8084
spring.datasource.url=jdbc:mysql://localhost:3306/healthcheck_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

## Building the Application
```bash
# Windows
mvnw.cmd clean install

# Mac/Linux
./mvnw clean install
```

## Running the Application
```bash
# Windows
mvnw.cmd spring-boot:run

# Mac/Linux
./mvnw spring-boot:run
```

The application starts on port 8084.

## API Endpoints

### Health Check Endpoint

**GET** `/healthz`

Checks application health and database connectivity.

**Request:**
- Method: GET only
- No request body allowed
- No query parameters allowed

**Response Status Codes:**
- `200 OK` - Service is healthy
- `400 Bad Request` - Request contains body or query parameters
- `405 Method Not Allowed` - Non-GET method used
- `503 Service Unavailable` - Database connection failed

**Examples:**
```bash
# Successful health check
curl -v http://localhost:8084/healthz

# Wrong method (returns 405)
curl -v -X POST http://localhost:8084/healthz

# With body (returns 400)
curl -v http://localhost:8084/healthz -d '{"test":"data"}'

# With query parameters (returns 400)
curl -v http://localhost:8084/healthz?param=value
```

## Database Schema

### Table: health_checks

| Column          | Type      | Constraints                     |
|-----------------|-----------|---------------------------------|
| check_id        | BIGINT    | PRIMARY KEY, AUTO_INCREMENT     |
| check_datetime  | DATETIME  | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

Each successful health check inserts a new record.

## Testing

### Verify Database Records
```sql
USE healthcheck_db;
SELECT * FROM health_checks;
```

### Test Database Failure

1. Stop MySQL service while application is running
2. Call health check endpoint - should return 503
3. Restart MySQL - endpoint should work again

## Dependencies

- Spring Boot Starter Web (REST API)
- Spring Boot Starter Data JPA (Database ORM)
- MySQL Connector/J (Database driver)
- Lombok (Code generation)
- Spring Boot Starter Test (Testing framework)

## Author

Sushmita Hubli
Northeastern University ID: 002303871
