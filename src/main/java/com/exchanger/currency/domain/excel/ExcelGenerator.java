package com.exchanger.currency.domain.excel;

import com.exchanger.currency.domain.currency.Currency;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Component
public class ExcelGenerator {
    private static final String SHEET_NAME = "Currencies";
    public byte[] generateExcelWorkbook(List<Currency> currencyResponseList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelMakerr(currencyResponseList).write(outputStream);
        return outputStream.toByteArray();
    }
    public byte[] generateExcelWorkbookWithExtensions(List<Currency> currencyResponseList) throws IOException {
        int i = currencyResponseList.size() + 1;
        Workbook workbook = excelMakerr(currencyResponseList);
        Row row = workbook.getSheet(SHEET_NAME).getRow(0);
        row.createCell(3).setCellValue("Average");
        Row row1 = workbook.getSheet(SHEET_NAME).getRow(1);
        row1.createCell(3).setCellFormula("AVERAGE(C2:C" + i + ")");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }
    public Workbook excelMakerr(List<Currency> currencyResponseList){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Code");
        row.createCell(2).setCellValue("Mid");
        for (int i = 0; i < currencyResponseList.size(); i++) {
            Row rows = sheet.createRow(i+1);
            rows.createCell(0).setCellValue(currencyResponseList.get(i).currency());
            rows.createCell(1).setCellValue(currencyResponseList.get(i).code());
            rows.createCell(2).setCellValue(currencyResponseList.get(i).mid());
        }
        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(3, true);
        return workbook;
    }
}
