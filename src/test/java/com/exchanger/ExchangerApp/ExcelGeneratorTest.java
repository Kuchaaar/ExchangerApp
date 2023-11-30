package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.exceptions.NoDataException;
import com.exchanger.currency.services.excel.CurrencyReportCurrencies;
import com.exchanger.currency.services.excel.CurrencyReportDatasource;
import com.exchanger.currency.services.excel.WorkbookGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

class ExcelGeneratorTest{

    @Test
    void testExcelFileToByteArray() throws IOException, NoDataException{
        // given
        CurrencyReportDatasource mockDatasource =
                new CurrencyReportDatasource(List.of(new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.5)),
                        new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.0))));
        WorkbookGenerator workbookGenerator = new WorkbookGenerator();
        Workbook workbook = new XSSFWorkbook("src/test/java/resources/response.xlsx");
        // then
        assertEquals(workbook,
                workbookGenerator.writeExcelFile(mockDatasource,true));
    }
}