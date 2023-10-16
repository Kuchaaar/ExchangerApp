package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.domain.exceptions.NoDataException;
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

    @PostMapping(value = "/dane", produces = "application/octet-stream")
    public byte[] excelResponse(@RequestBody ReportPeriod reportPeriod) throws IOException, NoDataException{
        return excelMaker.excelFileFromReportPeriod(reportPeriod);
    }

    @PostMapping(value = "/currency", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponse(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, false);
    }


    @PostMapping(value = "/currency/extensions", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponseWithExtensions(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return excelMaker.excelFileFromCurrencyReportPeriod(currencyReportPeriod, true);
    }

    @GetMapping("/daty") public List<LocalDate> getLocalDates(){
        return currencyService.availableDates();
    }

    @GetMapping("/code") public List<String> getCurrencyCodes(){
        return currencyService.availableCodes();
    }

    @PostMapping("/date/currency") public List<LocalDate> findLocalDatesForCurrency(@RequestBody String currencyCode){
        return currencyService.avilableDatesForCurrency(currencyCode);
    }

    @PostMapping("/cos")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponse(@RequestBody
                                                         FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyService.findCurrencyWithHighestRatePercentageChange(findCurrencyWithHighestRatePercentageChangeRequest);
    }
}