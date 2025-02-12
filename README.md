# Fitness App Project

## Author
**Name:** Artiom Varvarenko

## Overview
This application is a *Fitness App* designed for managing **Exercises**, **MuscleGroups**, and **Levels**.  
It includes:
- A console-based presentation layer (for certain profiles).
- A web-based interface (with Thymeleaf pages).
- JPA (and alternative) repository layers.
- Exporting to JSON.
- Basic authentication & session tracking (optional).
- And more!

### Domain Model (High-Level Explanation)
- **Exercise**: Represents a workout exercise (e.g., Push-up, Squat).
    - Has relationships to multiple **MuscleGroups** (many-to-many).
    - Has relationships to multiple **Level** objects (also many-to-many in this setup).

- **MuscleGroup**: Represents a muscle group category (e.g., Chest, Back, Legs).
    - Has a many-to-many relationship with **Exercise**.
    - Each muscle group can be linked to multiple exercises.

- **Level**: Represents difficulty levels (Beginner, Intermediate, Advanced).
    - Has a many-to-many relationship with **Exercise**.

## Profiles
The application has multiple Spring profiles to demonstrate different data layer implementations:

1. **`inmemory`**  
   Uses an in-memory list (via `DataFactory`) for storing data (no real DB).

2. **`jdbc`**  
   Uses plain JDBC with `JdbcTemplate` to connect to H2 or a real database.

3. **`jpa`**  
   Uses JPA with Hibernate to map entities to DB tables. This approach usually relies on `schema.sql` or `ddl-auto` to create the schema.

4. **`dev`**  
   Also uses JPA but sets up an H2 in-memory DB with `create-drop`, plus seeds data from `data.sql`.

5. **`prod`**  
   Uses JPA with a PostgreSQL DB in production.

6. **`datajpa`**  
   Introduces a `JpaRepository` approach (Spring Data JPA) instead of manual JPA repositories.

By switching `spring.profiles.active=someProfile`, you can choose which repository and configuration to load.

## Databases
- **H2** (in-memory) for development/testing (`dev`, `inmemory`, `jdbc` profiles).
    - URL: `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1`
    - Username: `sa`
    - Password: *(empty)*

- **PostgreSQL** for production (`prod` profile).
    - URL: `jdbc:postgresql://localhost:5432/yourdbname`
    - Username: `yourusername`
    - Password: `yourpassword`

Check `application-dev.properties` and `application-prod.properties` in the `src/main/resources` folder for details.

## Running the Project Smoothly
1. **Java Version**: Make sure you have **Java 17+** installed.
2. **Profiles**:
    - If you want to run with H2 in dev mode, use:
      ```bash
      ./gradlew bootRun --args='--spring.profiles.active=dev'
      ```  
      or set `spring.profiles.active=dev` in your IDE.
    - If you want an in-memory approach with no DB, use:
      ```bash
      ./gradlew bootRun --args='--spring.profiles.active=inmemory'
      ```
    - If you want to run with your local PostgreSQL, use:
      ```bash
      ./gradlew bootRun --args='--spring.profiles.active=prod'
      ```
      *(Make sure to fill in `username`, `password`, and `url` in `application-prod.properties`.)*
    - If you want to test the `datajpa` approach, do:
      ```bash
      ./gradlew bootRun --args='--spring.profiles.active=datajpa'
      ```
3. **Database**:
    - For the `dev` profile, H2 auto-creates tables via `spring.jpa.hibernate.ddl-auto=create-drop`.
    - For the `prod` profile, you must have a PostgreSQL instance running on the correct port and correct credentials in `application-prod.properties`. Also set `ddl-auto` to `validate` or `update` depending on your preference.

4. **Ports**: The default server port is **8080** (`server.port=8080` in `application.properties`).

## Start URL
Once the app is running, open:
http://localhost:8080/

- This leads you to the home page (`home.html`), from which you can navigate to the Exercises page, MuscleGroups, JSON export endpoints, etc.

## Parts Completed vs. Not Completed
- **Completed**:
    - Multiple repository implementations (inmemory, JDBC, JPA, Spring Data JPA).
    - Service layer with different profiles.
    - Basic web pages (Exercises, MuscleGroups).
    - JSON export with GSON.
    - Exception handling (ControllerAdvice, custom error pages).
    - Internationalization (messages.properties).
