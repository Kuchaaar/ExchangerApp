package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.peristence.currency.DatabaseJPACurrencyRepository;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:///testdb",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
class DatabaseJPACurrencyRepositoryTest {

    @Container
    private static final DockerComposeContainer<?> dockerComposeContainer =
            new DockerComposeContainer<>(new File("C:\\Users\\kuba_\\OneDrive\\Pulpit\\ExchangerApp\\src\\test\\java\\com\\exchanger\\ExchangerApp\\docker-compose-test.yml"))
                    .withExposedService("postgres", 5436);
    private static final LocalDate localDate = LocalDate.parse("2023-10-16");
    private final String currency1 = "currency1";
    private final String currency2 = "currency2";
    private final String currency3 = "currency3";
    private final String code1 = "ABC";
    private final String code2 = "DEF";
    private final String code3 = "GHI";
    private final BigDecimal bigDecimal1 = BigDecimal.valueOf(1.0);
    private final BigDecimal bigDecimal2 = BigDecimal.valueOf(10.0);
    private final BigDecimal bigDecimal3 = BigDecimal.valueOf(100.0);
    private final List<CurrencyResponse> currencyResponses = List.of(
            new CurrencyResponse(currency1, code1, bigDecimal1, localDate),
            new CurrencyResponse(currency2, code2, bigDecimal2, localDate),
            new CurrencyResponse(currency3, code3, bigDecimal3, localDate)
    );
    @Autowired
    private DatabaseJPACurrencyRepository repository;

    @BeforeAll
    static void startContainer(){
        dockerComposeContainer.start();
    }

    @AfterAll
    static void stopContainer(){
        dockerComposeContainer.stop();
    }

    @Test
    void saveAllTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(currencyResponses.size(), repository.findAll().size());
    }

    @Test
    void availableCodesTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(List.of("code1", "code2", "code3"), repository.availableCodes());
    }

    @Test
    void findByDatesTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(List.of(new Currency(currency1, code1, bigDecimal1, localDate),
                        new Currency(currency2, code2, bigDecimal2, localDate),
                        new Currency(currency3, code3, bigDecimal3, localDate))
                , repository.findByDates(localDate, localDate));
    }

    @Test
    void isDateInDataTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertTrue(repository.isDateInData(localDate));
    }

    @Test
    void availableDatesTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(List.of(localDate), repository.availableDates());
    }

    @Test
    void findAllTest(){
        // when
        repository.saveAll(currencyResponses);

        //then
        assertEquals(List.of(new Currency(currency1, code1, bigDecimal1, localDate),
                new Currency(currency2, code2, bigDecimal2, localDate),
                new Currency(currency3, code3, bigDecimal3, localDate)), repository.findAll());
    }

    @Test
    void availableDatesForCurrencyTest(){
        // when
        repository.saveAll(currencyResponses);

        //then
        assertEquals(List.of(localDate), repository.availableDatesForCurrency(code1));
    }

    @Test
    void findCurrencyByDatesTest(){
        // when
        repository.saveAll(currencyResponses);

        //then
        assertEquals(List.of(new Currency(currency1, code1, bigDecimal1, localDate)),
                repository.findCurrencyByDates(localDate, localDate, code1));
    }

    @Test
    void findCurrencyFromStartDateAndEndDateTest(){
        // when
        repository.saveAll(currencyResponses);

        //then
        assertEquals(List.of(
                new CurrencyFromStartDateAndEndDate(
                        new Currency(currency1, code1, bigDecimal1, localDate),
                        new Currency(currency1, code1, bigDecimal1, localDate)
                ),
                new CurrencyFromStartDateAndEndDate(
                        new Currency(currency2, code2, bigDecimal2, localDate),
                        new Currency(currency2, code2, bigDecimal2, localDate)
                ),
                new CurrencyFromStartDateAndEndDate(
                        new Currency(currency3, code3, bigDecimal3, localDate),
                        new Currency(currency3, code3, bigDecimal3, localDate))
        ),repository.findCurrencyFromStartDateAndEndDate(localDate,localDate));
    }
}