# Web Library API

This is a REST API project for managing Library-like application. Project is built using Java 17 and Spring Boot, and
includes Docker support with `docker-compose` and a `Dockerfile` for containerization.

## Features

- REST API to manage:
    - Books
    - Publishers
    - Authors
    - Users
- User roles via Spring Security
- API documentation available via Swagger UI at `/api/swagger-ui.html` (configurable in the project's configuration
  file)
- Configuration support using `.env` file
- Dockerized using `docker-compose.yaml` and `Dockerfile`

## Security

Currently, only basic authentication is supported. The API includes the following endpoints:

- **/login**: Authenticate users
- **/register**: Register new users
- **/logout**: Logout users

### User Roles

- **ADMINISTRATOR**: All rights
- **MANAGER**: Manages users
- **LIBRARIAN**: Creates books
- **READER**: Can view books, publishers, and authors

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Swagger** for API documentation
- **Docker** for containerization
- **PostgreSQL** as the database

**.env**

* DB_USERNAME=
* DB_PASSWORD=
* DB_URL=
* ADMIN_USERNAME=
* ADMIN_PASSWORD=