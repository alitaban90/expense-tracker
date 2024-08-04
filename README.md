# Expense Tracker Project

## Overview

The Expense Tracker project is a Java Spring Boot application that allows users to manage and track their expenses. It includes functionalities for adding and categorizing expenses, generating monthly reports, and setting custom alerts for spending patterns. The project also integrates Swagger for API documentation and supports Docker for containerization.

## Features

- **User Authentication**: Register and authenticate users.
- **Expense Management**: Add, view, and categorize expenses.
- **Alerts**: Create and evaluate alerts based on spending patterns.
- **Reports**: Generate monthly expense reports.

## Getting Started

### Prerequisites

- **Docker**: Install Docker to build and run the application in a containerized environment.
- **Docker Compose**: Install Docker Compose to manage multi-container Docker applications.

### Clone the Repository

```bash
git clone https://github.com/your-username/expense-tracker.git
cd expense-tracker
```

### Build and Run the Project with Docker

1. **Build the Docker Image**

   ```bash
   docker build -t expense-tracker .
   ```

2. **Run the Docker Container**

   ```bash
   docker run -p 8080:8080 expense-tracker
   ```

   This command maps port 8080 of the container to port 8080 of your host machine, allowing you to access the application at `http://localhost:8080`.

### Docker Compose (Optional)

If your project uses multiple services, you might have a `docker-compose.yml` file. To build and start all services:

```bash
docker-compose up --build
```

This command will build the images and start the containers as defined in the `docker-compose.yml` file.

### Configuration

Ensure that your Docker setup includes any necessary environment variables or configuration files. You might need to set up a `.env` file for database credentials or other settings.

## API Documentation

The API documentation is available through Swagger UI. Once the application is running, you can access Swagger UI at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Authentication

**POST /api/authentication/register**

Register a new user.

- **Request Body**: `UserRegisterDto`
- **Response**: `UserDto`

**POST /api/authentication/authenticate**

Authenticate a user and receive a JWT token.

- **Request Body**: `UserLoginDto`
- **Response**: `AuthenticationResponse`

### Expenses

**POST /api/expenses**

Add a new expense.

- **Request Body**: `ExpenseDto`
- **Response**: `ExpenseDto`

**GET /api/expenses**

Retrieve all expenses for the current user.

- **Response**: `List<ExpenseDto>`

**GET /api/expenses/report/monthly**

Retrieve expenses for a specified month.

- **Request Parameter**: `month` (format: yyyy-MM)
- **Response**: `List<ExpenseDto>`

### Categories

**POST /api/categories**

Add a new category.

- **Request Body**: `CategoryDto`
- **Response**: `CategoryDto`

**GET /api/categories**

Retrieve all categories.

- **Response**: `List<CategoryDto>`

### Alerts

**POST /api/alerts**

Add a new alert.

- **Request Body**: `AlertDto`
- **Response**: `AlertDto`

**GET /api/alerts**

Retrieve all alerts for the current user.

- **Response**: `List<AlertDto>`

## Testing

### Integration Tests

Integration tests are configured to run with an embedded database and real REST calls. To execute tests, use:

```bash
mvn test
```

### Unit Tests

Unit tests are located in `src/test/java` and cover various services and controllers. You can run them with:

```bash
mvn test
```

## Contributing

Contributions are welcome! Please fork the repository, make your changes, and submit a pull request.

---

Replace placeholder URLs and paths with the actual ones relevant to your project. If there are additional configurations or Docker-related details specific to your setup, you can include those as well!
