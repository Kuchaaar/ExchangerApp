package com.exchanger.ExchangerApp.web;

import com.exchanger.currency.services.excel.CurrencyReportPeriod;
import com.exchanger.currency.services.excel.ExcelMaker;
import com.exchanger.currency.services.excel.ReportPeriod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExcelControllerTest{
    private final ReportPeriod reportPeriod = new ReportPeriod(LocalDate.of(2023, 11, 11), LocalDate.of(2023, 12, 11));
    private final CurrencyReportPeriod currencyReportPeriod = new CurrencyReportPeriod(reportPeriod,"ABC");
    @Mock
    private ExcelMaker excelMaker;
    private final byte[] test = new byte[0];

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void excelResponseTest() throws IOException{
        when(excelMaker.excelFileFromReportPeriod(reportPeriod)).thenReturn(test);
        assertEquals(test,excelMaker.excelFileFromReportPeriod(reportPeriod));
    }
    @Test
    void oneCurrencyExcelResponseTest() throws IOException{
        when(excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, false)).thenReturn(test);
        assertEquals(test,excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, false));
    }
    @Test
    void oneCurrencyExcelResponseWithExtensionsTest() throws IOException{
        when(excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, true)).thenReturn(test);
        assertEquals(test,excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, true));
    }
}
