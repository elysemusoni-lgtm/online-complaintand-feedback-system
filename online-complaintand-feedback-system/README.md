# Online Complaint and Feedback System (Backend)

- **Student:** MUSONI Elyse
- **Student ID:** 27300
- **Project:** Online Complaint and Feedback System
- **Course:** INSY 8322 Web Technology and Internet, AUCA (Mid-Term Project)

Built with Spring Boot 3, Spring Data JPA and PostgreSQL (Java 17, Maven).

## Steps to run the API

1. Install Java 17, Maven and PostgreSQL.
2. Create the database: `CREATE DATABASE complaint_db;`
3. Open `src/main/resources/application.properties` and put your PostgreSQL password in `spring.datasource.password`.
4. Run the project: `mvn spring-boot:run`
5. The API starts on `http://localhost:8080`. The tables are created automatically.

## Progress

- Part A: domain model and CRUD (done)
- Part B: derived queries (done)
- Part C: JPQL with joins (done)
- Part D: business rules (done)
