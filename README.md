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

### 1. Interactive Testing (Swagger UI)

    Once the application is running, you can access the interactive API playground at: http://localhost:8080/swagger-ui/index.html

### 2. Static Documentation (No server required)

Review the API structure without running the server:

*   **API Documentation (PDF)** - Located in `./Docs/API/API_Documentation.pdf`. Detailed overview with example Requests and Responses.
*   **OpenAPI Spec (JSON)** - Located in `./Docs/API/API_Documentation.json`. Perfect for importing into Postman or Insomnia.

### 3. Business Logic Documentation

*   **Internal Javadoc Index** - Located in `./Docs/Javadoc/index.html`. Documentation on Service layer logic, business rules, and exception handling.

## Architecture & Design
The system implements a Layered Architecture to ensure maintainability and scalability:

*   **Controller Layer:** REST API endpoints and request validation.
*   **Service Layer:** Core business logic (capacity validation, revenue calculation).
*   **Repository Layer:** Data persistence and custom queries.
*   **DTO Pattern:** Strict separation between database entities and API contracts.
*   **Global Exception Handling:** Centralized `@ControllerAdvice` for consistent error reporting.

### Author
**Mateusz Salik**