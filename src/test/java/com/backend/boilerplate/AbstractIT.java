package com.backend.boilerplate;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class AbstractIT {
    static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:8.0.29");

    static {
        StringBuilder command = new StringBuilder("--character-set-server=utf8mb4 --collation-server=utf8mb4_0900_ai_ci");
        // MySQL 8.x has changed the default authentication plugin value so we need to explicitly configure it to get
        // the native password mechanism.
        // The reason we don't include when the tag is null is because with the TC version we use, MySQLContainer
        // defaults to
        command.append(" --default_authentication_plugin=caching_sha2_password");
        MYSQL_CONTAINER.withCommand(command.toString()).start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
    }
}
