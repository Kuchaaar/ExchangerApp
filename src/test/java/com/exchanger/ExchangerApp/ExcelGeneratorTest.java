package com.exchanger.ExchangerApp;

import com.exchanger.currency.services.excel.CurrencyReportCurrencies;
import com.exchanger.currency.services.excel.CurrencyReportDatasource;
import com.exchanger.currency.services.excel.WorkbookGenerator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelGeneratorTest{
    private final CurrencyReportDatasource mockDatasource =
            new CurrencyReportDatasource(List.of(new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.5)),
                    new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.0))));
    private final WorkbookGenerator workbookGenerator = new WorkbookGenerator();
    private final Workbook workbook;
    private static final String SHEET_NAME = "Currencies";
    {
        try{
            workbook = new XSSFWorkbook("src/test/java/resources/response.xlsx");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private final Workbook workbookTest = workbookGenerator.writeExcelFile(mockDatasource,true);
    Sheet s1 = workbook.getSheet(SHEET_NAME);
    Sheet s2 = workbookTest.getSheet(SHEET_NAME);
    @Test
    void testExcelSheetNumber(){
        assertEquals(workbook.getNumberOfSheets(), workbookTest.getNumberOfSheets());
    }
    @Test
    void testExcelRowNumber(){
        assertEquals(s1.getPhysicalNumberOfRows(), s2.getPhysicalNumberOfRows());
    }
}