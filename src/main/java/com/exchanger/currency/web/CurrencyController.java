package com.exchanger.currency.web;

import com.exchanger.currency.domain.JPAcurrency.CurrencyRepositoryJPA;
import com.exchanger.currency.domain.JPAcurrency.ExcelMakerJPA;
import com.exchanger.currency.domain.JPAcurrency.StringIntoCurrency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.domain.excel.ExcelMaker;
import com.exchanger.currency.domain.excel.DateConverter;
import com.exchanger.currency.domain.excel.CurrencyConverter;
import com.exchanger.currency.domain.excel.ReportPeriod;
import com.exchanger.currency.domain.excel.CurrencyReportPeriod;
import com.exchanger.currency.exceptions.NoDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@RestController
public class CurrencyController {
    private final CurrencyRepository currencyRepository;
    private final ExcelMaker excelMaker;
    private final DateConverter dateConverter;
    private final CurrencyConverter currencyConverter;
    private final CurrencyRepositoryJPA currencyRepositoryJPA;
    private final StringIntoCurrency stringIntoCurrency;


    private final ExcelMakerJPA excelMakerJPA;
    private static final String ATTACHMENT = "attachment";
    private static final String EXCEL_NAME = "excel.xlsx";

    public CurrencyController(CurrencyRepository currencyRepository,
                              ExcelMaker excelMaker, DateConverter dateConverter,
                              CurrencyConverter currencyConverter,
                              CurrencyRepositoryJPA currencyRepositoryJPA, StringIntoCurrency stringIntoCurrency, ExcelMakerJPA excelMakerJPA) {
        this.currencyRepository = currencyRepository;
        this.excelMaker = excelMaker;
        this.dateConverter = dateConverter;
        this.currencyConverter = currencyConverter;
        this.currencyRepositoryJPA = currencyRepositoryJPA;
        this.stringIntoCurrency = stringIntoCurrency;
        this.excelMakerJPA = excelMakerJPA;
    }

    @PostMapping(value = "/dane", produces = "application/octet-stream")
    public ResponseEntity<byte[]> excelResponse(@RequestBody String dates) throws IOException, NoDataException {
        ReportPeriod list = dateConverter.stringIntoReportPeriod(dates);

        return ResponseEntity.ok().headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME)).body(excelMaker.generateExcel(list));
    }

    @PostMapping(value = "/currency", produces = "application/octet-stream")
    public ResponseEntity<byte[]> oneCurrencyExcelResponse(@RequestBody String data) throws IOException, NoDataException {
        CurrencyReportPeriod currencyReportPeriod = currencyConverter.currencyConvertion(data);
        return ResponseEntity.ok().headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME)).body(excelMaker.generateExcelByCurrency(currencyReportPeriod));
    }

    @PostMapping(value = "/currency/extensions", produces = "application/octet-stream")
    public ResponseEntity<byte[]> oneCurrencyExcelResponseWithExtensions(@RequestBody String data) throws IOException, NoDataException {
        CurrencyReportPeriod currencyReportPeriod = currencyConverter.currencyConvertion(data);
        return ResponseEntity.ok().headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME)).body(excelMaker.generateExcelByCurrencyWithExtension(currencyReportPeriod));
    }

    @GetMapping("/daty")
    public List<String> getLocalDates() {
        return currencyRepository.availableDates();
    }













    @PostMapping(value = "/test")
    public ResponseEntity<byte[]> excelResponsev2(@RequestBody String dates) throws IOException, NoDataException {
        ReportPeriod reportPeriod = dateConverter.stringIntoReportPeriod(dates);
        return ResponseEntity.ok().headers(headers -> headers.add(ATTACHMENT, EXCEL_NAME)).body(excelMakerJPA.generateExcelByCurrency(reportPeriod));
    }
    @PostMapping(value = "/test/post")
    public void save(@RequestBody String dates){
        currencyRepositoryJPA.save(stringIntoCurrency.stringIntoCurrency(dates));
    }
    @GetMapping(value = "/test/daty")
    public List<String> getLocalDatesJPA() {
        return currencyRepositoryJPA.findDistinctDates();
    }
}