package com.exchanger.ExchangerApp.services.excel;

import com.exchanger.currency.domain.exceptions.NoDataException;
import com.exchanger.currency.services.excel.CurrencyReportCurrencies;
import com.exchanger.currency.services.excel.CurrencyReportDatasource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyReportDatasourceTest {
    private final List<CurrencyReportCurrencies> mockCurrencyReportCurrencies =
            List.of(new CurrencyReportCurrencies("name1", "code1", BigDecimal.valueOf(100)),
                    new CurrencyReportCurrencies("name2", "code2", BigDecimal.valueOf(110)));
    private final CurrencyReportDatasource currencyReportDatasource = new CurrencyReportDatasource(mockCurrencyReportCurrencies);


    @Test
    void validCurrencyReportDatasource() {
        assertEquals(currencyReportDatasource.averageMidPrice(), BigDecimal.valueOf(105));
    }

    @Test
    void invalidCurrencyReportDatasource() {
        List<CurrencyReportCurrencies> emptyList = List.of();
        assertThrows(NoDataException.class, () -> new CurrencyReportDatasource(emptyList));
    }

    @Test
    void validCurrencyByIndex() {
        assertEquals(new CurrencyReportCurrencies("name1", "code1", BigDecimal.valueOf(100)), currencyReportDatasource.currencyByIndex(0));
    }

    @Test
    void invalidCurrencyByIndex() {
        assertThrows(NoDataException.class,()->currencyReportDatasource.currencyByIndex(100));
    }

    @Test
    void dataSourceSize() {
        assertEquals(2, currencyReportDatasource.dataSourceSize());
    }
}