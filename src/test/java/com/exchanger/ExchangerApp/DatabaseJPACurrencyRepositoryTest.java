package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.peristence.currency.CurrencyRepositoryJPA;
import com.exchanger.currency.peristence.currency.DatabaseJPACurrencyRepository;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DatabaseJPACurrencyRepositoryTest{
    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8"))
            .withUsername("root")
            .withPassword("root")
            .withNetworkAliases("mysql")
            .withDatabaseName("test");
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
    private final List<Currency> currencies = List.of(
            new Currency(currency1,code1,bigDecimal1,localDate),
            new Currency(currency2,code2,bigDecimal2,localDate),
            new Currency(currency3,code3,bigDecimal3,localDate)
    );
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
    @Autowired
    private CurrencyRepositoryJPA currencyRepositoryJPA;

    private DatabaseJPACurrencyRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DatabaseJPACurrencyRepository(currencyRepositoryJPA);
        currencyRepositoryJPA.deleteAll();
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
    void saveAllTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(currencies, changeId(repository.findAll()));
    }

    @Test
    void availableCodesTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(List.of("ABC", "DEF", "GHI"), repository.availableCodes());
    }

    @Test
    void findByDatesTest(){
        // when
        repository.saveAll(currencyResponses);

        // then
        assertEquals(currencies, changeId(repository.findByDates(localDate, localDate)));
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
        assertEquals(currencies, changeId(repository.findAll()));
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
                changeId(repository.findCurrencyByDates(localDate, localDate, code1)));
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
        ), changeIdCurrencies(repository.findCurrencyFromStartDateAndEndDate(localDate, localDate)));
    }
    private List<Currency> changeId(List<Currency> list){
        for(Currency currency:list){
            currency.setId(null);
        }
        return list;
    }
    private List<CurrencyFromStartDateAndEndDate> changeIdCurrencies(List<CurrencyFromStartDateAndEndDate> list){
        for(CurrencyFromStartDateAndEndDate currency:list){
            currency.currencyFromStartDate().setId(null);
            currency.currencyFromEndDate().setId(null);
        }
        return list;
    }
}