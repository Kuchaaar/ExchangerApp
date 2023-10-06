package com.exchanger.currency.excel;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ExcelMaker {
    private final CurrencyRepository currencyRepository;

    public ExcelMaker(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    public ResponseEntity<byte[]> generateExcel(LocalDate startDate, LocalDate endDate) throws IOException {
        List<Currency> currencyResponseList = currencyRepository.findByDates(startDate,endDate);
//        if(currencyResponseList.isEmpty()){
//            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
//            return ResponseEntity.ok()
//                    .headers(new HttpHeaders())
//                    .body(outputStream2.toByteArray());
//        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Currencies");
        if(!currencyResponseList.isEmpty()) {
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
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "plik-excela.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }
}
