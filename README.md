# Simple CRUD Application

This is a minimal Java application demonstrating CRUD (Create, Read, Update, Delete) operations.

## Tech Stack

* **Java**
* **Spring Boot**: Used for the application framework.
* **Spring Data JPA**: Used for data persistence.
* **H2 Database**: An in-memory, embedded database.

## Building the Project

This is a standard Maven project. To build the project, run:

```bash
./mvnw clean package
````

## Running the Application

Once built, you can run the application:

```bash
java -jar target/*.jar
```

The application will start, and the H2 database will be initialized in memory.
