package com.exchanger.currency.domain.JPAcurrency;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelGeneratorJPA {

    public byte[] generateExcelWorkbook(List<CurrencyJPA> currencyResponseList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelMakerr(currencyResponseList).write(outputStream);
        return outputStream.toByteArray();
    }
    public Workbook excelMakerr(List<CurrencyJPA> currencyResponseList){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Currency");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Code");
        row.createCell(2).setCellValue("Mid");
        for (int i = 0; i < currencyResponseList.size(); i++) {
            Row rows = sheet.createRow(i+1);
            rows.createCell(0).setCellValue(currencyResponseList.get(i).getCurrencyName());
            rows.createCell(1).setCellValue(currencyResponseList.get(i).getCurrencyCode());
            rows.createCell(2).setCellValue(currencyResponseList.get(i).getExchangeRate());
        }
        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(3, true);
        return workbook;
    }
}
