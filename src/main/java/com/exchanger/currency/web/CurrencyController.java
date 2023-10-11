package com.exchanger.currency.web;

import com.exchanger.currency.services.excel.ExcelMaker;
import com.exchanger.currency.services.excel.ReportPeriod;
import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.domain.exceptions.NoDataException;
import com.exchanger.currency.services.excel.CurrencyReportPeriod;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class CurrencyController {
    private final CurrencyService currencyService;
    private final ExcelMaker excelMaker;

    public CurrencyController(CurrencyService currencyService, ExcelMaker excelMaker){
        this.currencyService = currencyService;
        this.excelMaker = excelMaker;
    }

    @PostMapping(value = "/dane", produces = "application/octet-stream")
    public byte[] excelResponse(@RequestBody ReportPeriod reportPeriod)
            throws IOException, NoDataException{
        return excelMaker.generateExcel(reportPeriod);
    }

    @PostMapping(value = "/currency", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponse(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return excelMaker.generateExcelByCurrency(currencyReportPeriod,false);
    }


    @PostMapping(value = "/currency/extensions", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponseWithExtensions(
            @RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return excelMaker.generateExcelByCurrency(currencyReportPeriod,true);
    }

    @GetMapping("/daty")
    public List<LocalDate> getLocalDates(){
        return currencyService.availableDates();
    }
}