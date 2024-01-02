package com.exchanger.ExchangerApp.services.excel;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.services.excel.CurrencyReportCurrencies;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyReportCurrenciesTest {
    private final Currency currency = new Currency(1L,"name1","code1",BigDecimal.valueOf(100),LocalDate.of(2023,10,10));
    @Test
    void CurrencyReportCurrenciesFromCurrency(){
        CurrencyReportCurrencies currencyReportCurrencies = CurrencyReportCurrencies.from(currency);
        assertEquals("name1",currencyReportCurrencies.name());
        assertEquals("code1",currencyReportCurrencies.code());
        assertEquals(BigDecimal.valueOf(100),currencyReportCurrencies.mid());
    }
}
