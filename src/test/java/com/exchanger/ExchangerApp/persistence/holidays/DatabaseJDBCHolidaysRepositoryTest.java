package com.exchanger.ExchangerApp.persistence.holidays;

import com.exchanger.currency.integration.holidays.HolidaysResponse;
import com.exchanger.currency.persistence.holidays.DatabaseHolidaysRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DatabaseHolidaysRepository.class})
@ImportAutoConfiguration(classes = {JdbcTemplateAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        JdbcRepositoriesAutoConfiguration.class})
@Sql({"classpath:holidays/holidays.sql"})
class DatabaseJDBCHolidaysRepositoryTest{
    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8"))
            .withUsername("root")
            .withPassword("root")
            .withNetworkAliases("mysql")
            .withDatabaseName("test");
    private final List<HolidaysResponse> holidaysResponses = List.of(
            new HolidaysResponse("2022-10-10", "test1"),
            new HolidaysResponse("2022-10-11", "test2"),
            new HolidaysResponse("2022-10-12", "test3")
    );
    @Autowired
    private DatabaseHolidaysRepository repository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }

    @BeforeAll
    static void startContainer(){
        MY_SQL_CONTAINER.start();
    }

    @AfterAll
    static void stopContainer(){
        MY_SQL_CONTAINER.stop();
    }

    @BeforeEach
    void setUp(){
        repository.deleteAllHolidays();
    }

    @Test
    void saveHolidays(){
        //when
        repository.saveHolidays(holidaysResponses);

        //then
        assertEquals(holidaysResponses, repository.findAllHolidays());
    }

    @Test
    void findAllHolidays(){
        //when
        repository.saveHolidays(holidaysResponses);

        //then
        assertEquals(holidaysResponses, repository.findAllHolidays());
    }
}