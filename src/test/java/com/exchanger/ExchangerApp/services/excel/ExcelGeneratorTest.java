package com.exchanger.ExchangerApp.services.excel;

import com.exchanger.currency.services.excel.CurrencyReportCurrencies;
import com.exchanger.currency.services.excel.CurrencyReportDatasource;
import com.exchanger.currency.services.excel.WorkbookGenerator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelGeneratorTest{
    private final CurrencyReportDatasource mockDatasource =
            new CurrencyReportDatasource(List.of(new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.5)),
                    new CurrencyReportCurrencies("EUR", "EU", BigDecimal.valueOf(1.0))));
    private final WorkbookGenerator workbookGenerator = new WorkbookGenerator();
    private final Workbook workbookFromFile;
    private static final String SHEET_NAME = "Currencies";

    {
        try{
            workbookFromFile = new XSSFWorkbook("src/test/resources/services/response.xlsx");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private final Workbook workbookTest = workbookGenerator.writeExcelFile(mockDatasource, true);
    Sheet sheetFromFile = workbookFromFile.getSheet(SHEET_NAME);
    Sheet sheetFromWorkbookTest = workbookTest.getSheet(SHEET_NAME);

    @Test
    void testExcelSheetNumber(){
        assertEquals(workbookFromFile.getNumberOfSheets(), workbookTest.getNumberOfSheets());
    }

    @Test
    void testExcelRowNumber(){
        assertEquals(sheetFromFile.getPhysicalNumberOfRows(), sheetFromWorkbookTest.getPhysicalNumberOfRows());
    }
    @Test
    void testExcelRowByRow(){
        List<String> workbookFromFile = new ArrayList<>();
        List<String> workbookFromWorkbookGenerator = new ArrayList<>();
        for(Row row : sheetFromFile){
            for(Cell cell : row){
                workbookFromFile.add(cell.toString());
            }
        }
        for(Row row : sheetFromWorkbookTest){
            for(Cell cell : row){
                workbookFromWorkbookGenerator.add(cell.toString());
            }
        }
        assertEquals(workbookFromFile,workbookFromWorkbookGenerator);
    }
}