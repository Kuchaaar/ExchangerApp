package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.domain.excel.*;
import com.exchanger.currency.exceptions.NoDataException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CurrencyController {
    private static final String ATTACHMENT = "attachment";
    private static final String EXCEL_NAME = "excel.xlsx";
    private final CurrencyRepository currencyRepository;
    private final ExcelMaker excelMaker;

    public CurrencyController(CurrencyRepository currencyRepository,
                              ExcelMaker excelMaker){
        this.currencyRepository = currencyRepository;
        this.excelMaker = excelMaker;
    }

    @PostMapping(value = "/dane", produces = "application/octet-stream")
    public ResponseEntity<byte[]> excelResponse(@RequestBody ReportPeriod reportPeriod)
            throws IOException, NoDataException{

        return ResponseEntity.ok()
                .headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME))
                .body(excelMaker.generateExcel(reportPeriod));
    }

    @PostMapping(value = "/currency", produces = "application/octet-stream")
    public ResponseEntity<Resource> oneCurrencyExcelResponse(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{

        byte[] excelData = excelMaker.generateExcelByCurrencyWithExtension(currencyReportPeriod);

        ByteArrayResource resource = new ByteArrayResource(excelData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + EXCEL_NAME);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }

    @PostMapping(value = "/currency/extensions", produces = "application/octet-stream")
    public ResponseEntity<byte[]> oneCurrencyExcelResponseWithExtensions(
            @RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return ResponseEntity.ok()
                .headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME))
                .body(excelMaker.generateExcelByCurrencyWithExtension(currencyReportPeriod));
    }

    @GetMapping("/daty")
    public List<String> getLocalDates(){
        return currencyRepository.availableDates();
    }


    @PostMapping(value = "/test")
    public ResponseEntity<byte[]> excelResponsev2(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return ResponseEntity.ok()
                .headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME))
                .body(excelMaker.generateExcelByCurrency(currencyReportPeriod));
    }
}