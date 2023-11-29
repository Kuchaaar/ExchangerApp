package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.exceptions.NoDataException;
import com.exchanger.currency.services.excel.CurrencyReportCurrencies;
import com.exchanger.currency.services.excel.CurrencyReportDatasource;
import com.exchanger.currency.services.excel.ExcelGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExcelGeneratorTest {

    @Test
    void testExcelFileToByteArray() throws IOException, NoDataException{
        // given
        CurrencyReportDatasource mockDatasource = mock(CurrencyReportDatasource.class);
        when(mockDatasource.dataSourceSize()).thenReturn(2);
        when(mockDatasource.currencyByIndex(0)).thenReturn(new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.5)));
        when(mockDatasource.currencyByIndex(1)).thenReturn(new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.0)));
        when(mockDatasource.averageMidPrice()).thenReturn(BigDecimal.valueOf(1.3));
        ExcelGenerator excelGenerator = new ExcelGenerator();

        // when
        byte[] result = excelGenerator.excelFileToByteArray(mockDatasource, true);

        // then
        assertArrayEquals(convertExcelToByteArray(), result);
    }
    private static byte[] convertExcelToByteArray() throws IOException {
        try (
                FileInputStream fis = new FileInputStream("src/test/java/com/exchanger/ExchangerApp/response.xlsx");
             Workbook workbook = WorkbookFactory.create(fis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream())
        {
            workbook.write(bos);
            return bos.toByteArray();
        }
    }
}
