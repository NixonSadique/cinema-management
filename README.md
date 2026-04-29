# Cinema Management App

## Introduction

REST API for management of a Cinema.
> Examples include user management,authentication, showtime management and much more.

## Technologies

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* Spring Security
* Spring Actuator
* JWT authentication
* Maven
* H2 / MySQL
* OpenDoc (Swagger)
* SLF4J
* JUnit 5
* Mockito

## Setup & Installation

### 1. Clone the repository

When on your desired folder, run:

```bash
git clone https://github.com/NixonSadique/cinema-management.git
cd cinema-management
```

### 2. Configure environment

Update `application.properties`(or `application.yaml` if you prefer) to the following properties:

```properties
jwt.secret=YOUR-JWT-SECRET-HERE
#spring.profiles.active=dev // To use the H2 Database
#spring.profiles.active=prod // To use the MySQL Database
```

#### 2.1. Configuring the dev environment(H2 Database)

You can remove the `logging.level.web=DEBUG` to reduce the amount of logs in the console

```properties
spring.datasource.username=sa
spring.datasource.password=password
spring.datasource.url=jdbc:h2:mem:cinema
logging.level.web=DEBUG
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.show-sql=true
```

#### 2.2. Configuring the prod environment

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Run the application

If you have Maven installed:

```bash
mvn spring-boot:run
```

Or using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

### 4. Access the application

Once started, by default the application will be available at:

```
http://localhost:8080
```

Or check the console for the port in which it is running.

### 5. Build the application

To build the project:

```bash
./mvnw clean install
```

The generated JAR will be located in the target/ directory.

## Project Structure

```
src/
├── main/
│   ├── java/com/nixon/cinema/
│   │   ├── configuration/      # App configuration
│   │   ├── controller/         # REST controllers
│   │   ├── dto/
│   │   │   ├── request/        # Incoming API payloads
│   │   │   └── response/       # Outgoing API payloads
│   │   ├── exceptions/
│   │   │   └── handler/        # Global exception handling
│   │   ├── model/
│   │   │   └── enums/          # Domain enums
│   │   ├── repository/         # JPA repositories
│   │   ├── security/           # Security & JWT logic
│   │   └── service/
│   │       └── impl/           # Business logic implementations
│   │
│   └── resources/
│       └── static/             # Static assets
└── test/
    ├── java/com/nixon/cinema/
    │   ├── repository/         # Repository tests
    │   └── service/
    │       └── impl/           # Service tests
    │
    └── resources/
```

## API documentation

Once you run the application you can find the swagger documentation in:

```
http://localhost:8080/swagger-ui.html
```

## Diagrams

### Class Diagram

The following diagram presents the core domain model of the application, showing the main entities,
their fields, and the relationships between them.

![Class diagram](/assets/Class_Diagram.jpg)

### Sequence diagrams

The following diagrams represent the flow of some important processes:

#### 1. Login

![Sequence diagram for login](/assets/Sequence-Diagram_login.jpg)


