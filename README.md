# Gym Management System

A Spring Boot-based backend application for managing gym facilities, membership plans, and members.

## Tech Stack

*   **Java 21**
*   **Spring Boot 3.x**
*   **Database:** H2 (In-memory)
*   **Documentation:** OpenAPI (Swagger), Javadoc
*   **Build Tool:** Maven

## Installation & Running

1. **Clone the repository:**

    git clone https://github.com/matusalik/SiiGymManagement.git

2. **Navigate to the project directory**

    cd sii_gym_management_api

3. **Run the application:**

    mvnw spring-boot:run

## API & Technical Documentation
All project documentation is located in the /Docs directory.

1. **Interactive Testing (Swagger UI)**

Once the application is running, you can access the interactive API playground at:
http://localhost:8080/swagger-ui/index.html

2. **Static Documentation (No server required)**

If you prefer not to run the application, you can review the API structure and examples in the Docs/API/ folder:

API Documentation (PDF) - Contains all endpoints with example Requests and Responses.

OpenAPI Specification (JSON) - Standard spec file for import into e.g. Postman.

3. **Business Logic Documentation**

Internal Javadoc Index - Technical documentation for the Service layer, explaining the internal logic and business rules.


## Architecture Note
The project follows a layered architecture (Controller -> Service -> Repository) with a clear separation between Database Entities and API Data Transfer Objects (DTOs). It includes global exception handling and idempotency checks (e.g., preventing duplicate status activations).

**Created by Mateusz Salik (matusalik)**