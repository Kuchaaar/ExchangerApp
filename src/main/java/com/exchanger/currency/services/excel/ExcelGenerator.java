package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.exceptions.NoDataException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExcelGenerator {
    private static final String SHEET_NAME = "Currencies";

    public byte[] generateExcelWorkbook(CurrencyReportDatasource currencyReportDatasource, boolean isExtension)
            throws IOException, NoDataException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelMakerr(currencyReportDatasource, isExtension).write(outputStream);
        return outputStream.toByteArray();
    }

    public Workbook excelMakerr(CurrencyReportDatasource currencyReportDatasource, boolean isExtension){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Code");
        row.createCell(2).setCellValue("Mid");
        for (int i = 0; i < currencyReportDatasource.dataSourceSize(); i++) {
            Row rows = sheet.createRow(i + 1);
            CurrencyReportCurrencies currencyReportCurrencies = currencyReportDatasource.currencyByIndex(i);
            rows.createCell(0).setCellValue(currencyReportCurrencies.name());
            rows.createCell(1).setCellValue(currencyReportCurrencies.code());
            rows.createCell(2).setCellValue(currencyReportCurrencies.mid());
        }
        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(3, true);
        if(isExtension){
            row.createCell(3).setCellValue("Average");
            Row row1 = workbook.getSheet(SHEET_NAME).getRow(1);
            row1.createCell(3).setCellValue(currencyReportDatasource.averageMidPrice());
        }
        return workbook;
    }
}