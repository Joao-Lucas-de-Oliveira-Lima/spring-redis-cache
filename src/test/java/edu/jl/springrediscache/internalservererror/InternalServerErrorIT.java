package edu.jl.springrediscache.internalservererror;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.mock.CarMock;
import edu.jl.springrediscache.service.implementation.CarServiceImplementation;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class InternalServerErrorIT extends CarMock {
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:latest");

    @Container
    @ServiceConnection
    private static final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>("redis:latest").withExposedPorts(6379);

    @LocalServerPort
    private int port;

    @MockBean
    private CarServiceImplementation carService;

    @PostConstruct
    void init() {
        Mockito.doThrow(new RuntimeException("Error deleting car"))
                .when(carService).deleteById(any(Long.class));
        Mockito.doThrow(new RuntimeException("Get a car"))
                .when(carService).findByIdWithCacheSupport(any(Long.class));
        Mockito.doThrow(new RuntimeException("Get a car"))
                .when(carService).findByIdWithoutCacheSupport(any(Long.class));
        Mockito.doThrow(new RuntimeException("Save a car"))
                .when(carService).save(any(CarRequestDTO.class));
        Mockito.doThrow(new RuntimeException("Update a car"))
                .when(carService).updateById(any(Long.class), any(CarRequestDTO.class));
        Mockito.doThrow(new RuntimeException("Page search a car"))
                .when(carService).findByModelContainingIgnoreCase(any(String.class), any(Pageable.class));
    }

    @BeforeEach
    void setupForEachTest() {
        RestAssured.port = port;
    }

    private final String defaultMessageFor500Errors = "An unexpected error occurred. Please try again later or contact support if the problem persists.";

    @Test
    @DisplayName("Should return HTTP 500 with a default error message when an exception occurs while deleting a car by ID")
    void shouldReturnInternalServerErrorWhenDeletingCarById() {
        given()
                .contentType("application/json")
                .when()
                .delete("/api/v1/cars/{id}", 1L)
                .then()
                .statusCode(500)
                .body("timestamp", is(notNullValue()))
                .body("message", equalTo(defaultMessageFor500Errors))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 500 with a default error message when an exception occurs while fetching a cached car by ID")
    void shouldReturnInternalServerErrorWhenFetchingCachedCarById() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars/cacheable/{id}", 1L)
                .then()
                .statusCode(500)
                .body("timestamp", is(notNullValue()))
                .body("message", equalTo(defaultMessageFor500Errors))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 500 with a default error message when an exception occurs while fetching a car by ID")
    void shouldReturnInternalServerErrorWhenFetchingCarById() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars/{id}", 1L)
                .then()
                .statusCode(500)
                .body("timestamp", is(notNullValue()))
                .body("message", equalTo(defaultMessageFor500Errors))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 500 with a default error message when an exception occurs while saving a car")
    void shouldReturnInternalServerErrorWhenSavingCar() {
        given()
                .contentType("application/json")
                .when()
                .body(VALID_CAR)
                .post("/api/v1/cars")
                .then()
                .statusCode(500)
                .body("timestamp", is(notNullValue()))
                .body("message", equalTo(defaultMessageFor500Errors))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 500 with a default error message when an exception occurs while updating a car by ID")
    void shouldReturnInternalServerErrorWhenUpdatingCarById() {
        given()
                .contentType("application/json")
                .when()
                .body(VALID_CAR)
                .put("/api/v1/cars/{id}", 1L)
                .then()
                .statusCode(500)
                .body("timestamp", is(notNullValue()))
                .body("message", equalTo(defaultMessageFor500Errors))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 500 with a default error message when an exception occurs while fetching a paginated list of cars")
    void shouldReturnInternalServerErrorWhenFetchingCarPage() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars")
                .then()
                .statusCode(500)
                .body("timestamp", is(notNullValue()))
                .body("message", equalTo(defaultMessageFor500Errors))
                .body("details", is(notNullValue()));
    }

}
