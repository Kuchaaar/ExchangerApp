package com.exchanger.ExchangerApp.services.sheduling;

import com.exchanger.currency.integration.currency.CurrenciesResponse;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.peristence.currency.InMemoryCurrencyRepository;
import com.exchanger.currency.services.sheduling.DatabaseChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseCheckerTest {
    private InMemoryCurrencyRepository inMemoryCurrencyRepository;
    private DatabaseChecker databaseChecker;
    private final String currency1 = "currency1";
    private final String currency2 = "currency2";
    private final String currency3 = "currency3";
    private final String code1 = "ABC";
    private final String code2 = "DEF";
    private final String code3 = "GHI";
    private final BigDecimal bigDecimal1 = BigDecimal.valueOf(1.0);
    private final BigDecimal bigDecimal2 = BigDecimal.valueOf(10.0);
    private final BigDecimal bigDecimal3 = BigDecimal.valueOf(100.0);
    private static final LocalDate localDate = LocalDate.parse("2023-10-16");
    private final LocalDate localDateTest = LocalDate.parse("2023-10-16");
    private final List<CurrencyResponse> currencyResponses = List.of(
            new CurrencyResponse(currency1,code1,bigDecimal1,localDate),
            new CurrencyResponse(currency2,code2,bigDecimal2,localDate),
            new CurrencyResponse(currency3,code3,bigDecimal3,localDate)
            );
    private final List<CurrenciesResponse> currenciesResponse = List.of(
            new CurrenciesResponse("2023-10-16",currencyResponses));

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        inMemoryCurrencyRepository = new InMemoryCurrencyRepository();
        databaseChecker = new DatabaseChecker(inMemoryCurrencyRepository);
        inMemoryCurrencyRepository.saveAll(currencyResponses);
    }

    @Test
    void ifDataInDatabase(){
        assertTrue(databaseChecker.ifDataInDatabase(currenciesResponse));
    }

    @Test
    void ifDateInDatabase(){
        assertTrue(inMemoryCurrencyRepository.isDateInData(localDateTest));
    }
}