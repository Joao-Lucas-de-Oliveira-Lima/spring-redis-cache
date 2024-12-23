package edu.jl.springrediscache;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SpringRedisCacheApplication}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringRedisCacheApplicationIT {
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringRedisCacheApplicationIT(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

}