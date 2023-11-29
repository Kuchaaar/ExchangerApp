package com.exchanger.currency.web;

import com.exchanger.currency.services.excel.CurrencyReportPeriod;
import com.exchanger.currency.services.excel.ExcelMaker;
import com.exchanger.currency.services.excel.ReportPeriod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExcelController {
    private final ExcelMaker excelMaker;

    public ExcelController(ExcelMaker excelMaker){
        this.excelMaker = excelMaker;
    }

    @PostMapping(value = "/excel/currency/all", produces = "application/octet-stream")
    public byte[] excelResponse(@RequestBody ReportPeriod reportPeriod) throws IOException{
        return excelMaker.excelFileFromReportPeriod(reportPeriod);
    }

    @PostMapping(value = "/excel/currency/by_one", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponse(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException{
        return excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, false);
    }


    @PostMapping(value = "/excel/currency/extensions", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponseWithExtensions(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException{
        return excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, true);
    }
}
