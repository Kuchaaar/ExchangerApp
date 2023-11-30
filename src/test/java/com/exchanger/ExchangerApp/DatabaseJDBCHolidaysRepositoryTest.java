package com.exchanger.ExchangerApp;

import com.exchanger.currency.peristence.holidays.DatabaseHolidaysRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
@SpringBootTest
class DatabaseJDBCHolidaysRepositoryTest{
    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8"))
            .withUsername("root")
            .withPassword("root")
            .withNetworkAliases("mysql")
            .withDatabaseName("test");
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
    @Autowired
    DataSource dataSource;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DatabaseHolidaysRepository repository;
    @BeforeEach
    void setUp() {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        repository = new DatabaseHolidaysRepository(namedParameterJdbcTemplate);
        repository.deleteAllHolidays();
    }
    @BeforeAll
    static void startContainer(){
        MY_SQL_CONTAINER.start();
    }

    @AfterAll
    static void stopContainer(){
        MY_SQL_CONTAINER.stop();
    }

    @Test
    void saveHolidays(){

    }


    @Test
    void findHolidaysByYear(){
    }
}
