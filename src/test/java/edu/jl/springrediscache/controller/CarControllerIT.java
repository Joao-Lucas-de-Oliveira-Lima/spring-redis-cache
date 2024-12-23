package edu.jl.springrediscache.controller;

import edu.jl.springrediscache.dto.car.CarResponseDTO;
import edu.jl.springrediscache.mock.CarMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for {@link CarControllerIT}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CarControllerIT extends CarMock {

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

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    private static Stream<Arguments> provideInvalidCars() {
        return Stream.of(
                Arguments.of(INVALID_CAR_WITH_NULL_MAKE),
                Arguments.of(INVALID_CAR_WITH_BLANK_MAKE),
                Arguments.of(INVALID_CAR_WITH_EMPTY_MAKE),
                Arguments.of(INVALID_CAR_WITH_NULL_MODEL),
                Arguments.of(INVALID_CAR_WITH_BLANK_MODEL),
                Arguments.of(INVALID_CAR_WITH_EMPTY_MODEL),
                Arguments.of(INVALID_CAR_WITH_YEAR_ABOVE_CURRENT),
                Arguments.of(INVALID_CAR_WITH_YEAR_BELOW_1900)
        );
    }

    @Test
    @DisplayName("Verify PostgreSQL container is running")
    void shouldVerifyPostgresDatabaseConnection() {
        assertThat(POSTGRESQL_CONTAINER.isCreated()).isTrue();
        assertThat(POSTGRESQL_CONTAINER.isRunning()).isTrue();
    }

    @Test
    @DisplayName("Verify Redis container is running")
    void shouldVerifyRedisDatabaseConnection() {
        assertThat(REDIS_CONTAINER.isCreated()).isTrue();
        assertThat(REDIS_CONTAINER.isRunning()).isTrue();
    }

    @Test
    @DisplayName("Save a valid car and return HTTP 201")
    void shouldReturnCreatedWhenSavingValidCar() {
        given()
                .contentType("application/json")
                .body(VALID_CAR)
                .when()
                .post("/api/v1/cars")
                .then()
                .statusCode(201)
                .body("make", equalTo(VALID_CAR.getMake()))
                .body("model", equalTo(VALID_CAR.getModel()))
                .body("yearOfRelease", equalTo(VALID_CAR.getYearOfRelease().intValue()))
                .body("id", is(notNullValue()));
    }

    @ParameterizedTest
    @DisplayName("Save an invalid car and return HTTP 400")
    @MethodSource("provideInvalidCars")
    void shouldReturnBadRequestWhenSavingInvalidCar(Object invalidCar) {
        given()
                .contentType("application/json")
                .body(invalidCar)
                .when()
                .post("/api/v1/cars")
                .then()
                .statusCode(400)
                .body("timestamp", is(notNullValue()))
                .body("message", is(notNullValue()))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 200 when fetching an existing car by ID from the database")
    void shouldReturnOkWhenFetchingCarByIdFromDatabase() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars/{id}", VALID_CAR_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) VALID_CAR_ID))
                .body("make", is(notNullValue()))
                .body("model", is(notNullValue()))
                .body("yearOfRelease", is(notNullValue()))
                .body("make", not(emptyString()))
                .body("model", not(emptyString()))
                .body("yearOfRelease", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 200 when fetching an existing car by ID from the cache")
    void shouldReturnOkWhenFetchingCarByIdFromCache() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars/cacheable/{id}", VALID_CAR_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) VALID_CAR_ID))
                .body("make", is(notNullValue()))
                .body("model", is(notNullValue()))
                .body("yearOfRelease", is(notNullValue()))
                .body("make", not(emptyString()))
                .body("model", not(emptyString()))
                .body("yearOfRelease", is(notNullValue()));
    }


    @Test
    @DisplayName("Should return a paginated list of cars")
    void shouldReturnPageOfCars() {
        int pageSize = 40;
        int pageNumber = 0;

        Response response = given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars?size={size}&page={page}", pageSize, pageNumber)
                .then()
                .statusCode(200)
                .body("content", hasSize(pageSize))
                .body("page.size", equalTo(pageSize))
                .body("page.number", equalTo(pageNumber))
                .body("page.totalElements", is(notNullValue()))
                .body("page.totalPages", is(notNullValue()))
                .extract().response();

        int totalElements = response.jsonPath().getInt("page.totalElements");
        int totalPages = response.jsonPath().getInt("page.totalPages");
        assertThat(totalPages).isEqualTo((int) Math.ceil((double) totalElements / pageSize));

        List<CarResponseDTO> carResponseDtoList = response.jsonPath().getList("content", CarResponseDTO.class);
        carResponseDtoList.forEach(carResponseDto -> {
            assertThat(carResponseDto.getMake()).isNotBlank();
            assertThat(carResponseDto.getModel()).isNotBlank();
            assertThat(carResponseDto.getId()).isNotNull();
        });
    }

    @Test
    @DisplayName("Should return ResourceNotFoundException when car is not found by invalid ID")
    void shouldReturnResourceNotFoundExceptionWhenFindingCarWithInvalidId() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars/{id}", INVALID_CAR_ID)
                .then()
                .statusCode(404)
                .body("timestamp", is(notNullValue()))
                .body("message", is(notNullValue()))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should delete a car and return HTTP 204")
    void shouldDeleteACar() {
        given()
                .contentType("application/json")
                .when()
                .delete("/api/v1/cars/{id}", HIGHEST_VALID_CAR_ID)
                .then()
                .statusCode(204)
                .body(emptyOrNullString());


        given()
                .contentType("application/json")
                .when()
                .get("/api/v1/cars/{id}", HIGHEST_VALID_CAR_ID)
                .then()
                .statusCode(404)
                .body("timestamp", is(notNullValue()))
                .body("message", is(notNullValue()))
                .body("details", is(notNullValue()));
    }

    @Test
    @DisplayName("Should return HTTP 200 when updating a valid car")
    void shouldReturnOKWhenUpdatingAValidCar() {
        given()
                .contentType("application/json")
                .body(VALID_CAR_UPDATE)
                .when()
                .put("/api/v1/cars/{id}", VALID_CAR_ID)
                .then()
                .statusCode(200)
                .body("make", equalTo(VALID_CAR_UPDATE.getMake()))
                .body("model", equalTo(VALID_CAR_UPDATE.getModel()))
                .body("yearOfRelease", equalTo(VALID_CAR_UPDATE.getYearOfRelease().intValue()))
                .body("id", equalTo((int) VALID_CAR_ID));
    }

    @Test
    @DisplayName("Should return ResourceNotFoundException when updating a car with invalid ID")
    void shouldReturnResourceNotFoundExceptionWhenUpdatingAInvalidCar() {
        given()
                .contentType("application/json")
                .body(VALID_CAR_UPDATE)
                .when()
                .put("/api/v1/cars/{id}", INVALID_CAR_ID)
                .then()
                .statusCode(404)
                .body("timestamp", is(notNullValue()))
                .body("message", is(notNullValue()))
                .body("details", is(notNullValue()));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCars")
    @DisplayName("Should return HTTP 400 when updating a car with invalid data")
    void shouldReturnBadRequestExceptionWhenUpdatingAInvalidCar(Object invalidCar) {
        given()
                .contentType("application/json")
                .body(invalidCar)
                .when()
                .put("/api/v1/cars/{id}", VALID_CAR_ID)
                .then()
                .statusCode(400)
                .body("timestamp", is(notNullValue()))
                .body("message", is(notNullValue()))
                .body("details", is(notNullValue()));
    }
}