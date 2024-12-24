# Spring Redis Cache

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Joao-Lucas-de-Oliveira-Lima_spring-redis-cache&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Joao-Lucas-de-Oliveira-Lima_spring-redis-cache)

This project demonstrates a Spring Boot application leveraging Redis as a cache to enhance performance. It uses the `Spring Boot Starter Cache` annotations to manage how data is stored and evicted in Redis.

### Example Cache Usage
```java
@Caching(
    evict = @CacheEvict(value = "carPage", allEntries = true),
    put = @CachePut(value = "car", key = "#id")
)
public CarResponseDTO updateById(Long id, CarRequestDTO update) throws ResourceNotFoundException {
    // method implementation
}
```

---

## Prerequisites

Ensure you have the following tools installed:

- [**Java JDK 21**](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [**Maven 3.x or later**](https://maven.apache.org/download.cgi)
- [**Docker Desktop**](https://www.docker.com/products/docker-desktop/)

---

## Installation

### 1. Start the Databases
Run the following command to start the required databases using Docker Compose:
```bash
docker-compose up -d
```

### 2. Build and Run the Application
Execute the following commands to build and start the application:
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
- **API Documentation in JSON:** `/v3/api-docs`

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
Fetches a car record without using the cache.

#### **GET** `/api/v1/cars/cacheable/{id}`
Fetches a car record using the cache.

#### **PUT** `/api/v1/cars/{id}`
Updates a car record by its ID.

#### **DELETE** `/api/v1/cars/{id}`
Deletes a car record by its ID.

