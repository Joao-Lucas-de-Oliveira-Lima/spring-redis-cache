# Spring Redis Cache

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Joao-Lucas-de-Oliveira-Lima_spring-redis-cache&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Joao-Lucas-de-Oliveira-Lima_spring-redis-cache)

This project demonstrates a Spring Boot application that uses Redis as a cache to enhance performance. 

### Example Output
After the Spring context is fully initialized, the following log demonstrates the results of calling the `findById` endpoints:
```log
Starting the performance tests:

CarResponseDTO(id=1, make=Honda, model=Odyssey, yearOfRelease=2007)
CarResponseDTO(id=1, make=Honda, model=Odyssey, yearOfRelease=2007)
CarResponseDTO(id=1, make=Honda, model=Odyssey, yearOfRelease=2007)

Elapsed time for method calls WITH cache support: 457 milliseconds.

CarResponseDTO(id=1, make=Honda, model=Odyssey, yearOfRelease=2007)
CarResponseDTO(id=1, make=Honda, model=Odyssey, yearOfRelease=2007)
CarResponseDTO(id=1, make=Honda, model=Odyssey, yearOfRelease=2007)

Elapsed time for method calls WITHOUT cache support: 551 milliseconds.
```

---

## Prerequisites

- [**Java JDK 21**](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [**Maven 3.x or later**](https://maven.apache.org/download.cgi)
- [**Docker Desktop**](https://www.docker.com/products/docker-desktop/)

---

## Installation

1. **Start the databases:**
   ```bash
   docker-compose up -d
   ```

2. **Build and run the application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

## Running Tests

To execute the test suite, run:
```bash
mvn verify
```
> **Note:** Ensure Docker is running, as the application uses Testcontainers during testing.

---

## API Endpoints

### Swagger Documentation
Interactive API documentation is available at:
- **Swagger UI:** `/swagger-ui/index.html`
- **OpenAPI Specification:** `/v3/api-docs`

---

### API Overview

#### **POST** `/api/v1/cars`
Creates a new car record.

**Request Body (CarRequestDTO):**
```json
{
    "make": "Honda",
    "model": "Civic",
    "yearOfRelease": 2022
}
```

**Response (CarResponseDTO):**
```json
{
    "id": 1,
    "make": "Honda",
    "model": "Civic",
    "yearOfRelease": 2022
}
```

#### **GET** `/api/v1/cars`
Returns a paginated list of cars.

#### **GET** `/api/v1/cars/{id}`
Fetches a car without cache.

#### **GET** `/api/v1/cars/cacheable/{id}`
Fetches a car with cache.

#### **PUT** `/api/v1/cars/{id}`
Updates a car by its ID.

#### **DELETE** `/api/v1/cars/{id}`
Deletes a car by its ID.

