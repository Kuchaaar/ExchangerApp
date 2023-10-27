package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import com.exchanger.currency.services.excel.CurrencyReportPeriod;
import com.exchanger.currency.services.excel.ExcelMaker;
import com.exchanger.currency.services.excel.ReportPeriod;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost")
@RestController
public class CurrencyController {
    private final CurrencyService currencyService;
    private final ExcelMaker excelMaker;

    public CurrencyController(CurrencyService currencyService,
                              ExcelMaker excelMaker){
        this.currencyService = currencyService;
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

    @GetMapping("/available_dates") public List<LocalDate> getLocalDates(){
        return currencyService.availableDates();
    }

    @GetMapping("/available_codes") public List<String> getCurrencyCodes(){
        return currencyService.availableCodes();
    }

    @PostMapping("/available_dates/currency") public List<LocalDate> findLocalDatesForCurrency(@RequestBody String currencyCode){
        return currencyService.availableDatesForCurrency(currencyCode);
    }

    @PostMapping("/currency/highest_rate_percentage_change")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponse(@RequestBody
                                                                                                                   FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyService.findCurrencyWithHighestRatePercentageChange(findCurrencyWithHighestRatePercentageChangeRequest);
    }
}