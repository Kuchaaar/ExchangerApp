package com.exchanger.currency.excel;

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
    public byte[] generateExcelWorkbook(List<Currency> currencyResponseList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Currencies");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Code");
        row.createCell(2).setCellValue("Mid");
        for (int i = 1; i < currencyResponseList.size(); i++) {
            Row rows = sheet.createRow(i);
            rows.createCell(0).setCellValue(currencyResponseList.get(i).currency());
            rows.createCell(1).setCellValue(currencyResponseList.get(i).code());
            rows.createCell(2).setCellValue(currencyResponseList.get(i).mid());
        }
        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(3, true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }
}
