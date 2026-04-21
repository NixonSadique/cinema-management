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

Update `application.properties`(or `application.yaml` if you prefer):

```properties
jwt.secret=YOUR-JWT-SECRET-HERE
#spring.profiles.active=dev // To use the H2 Database
#spring.profiles.active=prod // To use the MySQL Database
```

#### 2.1. Configuring the dev environment(H2 Database)

You can remove the `logging.level.web=DEBUG` to reduce the ammount of logs in the console

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

## Run the application

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

## API documentation

Once you run the application you can find the swagger documentation in:

```
http://localhost:8080/swagger-ui.html
```

