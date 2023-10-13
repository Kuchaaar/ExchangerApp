package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.domain.exceptions.NoDataException;
import com.exchanger.currency.services.currencychange.CurrencyChangeService;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
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
    private final CurrencyChangeService currencyChangeService;

    public CurrencyController(CurrencyService currencyService,
                              ExcelMaker excelMaker,
                              CurrencyChangeService currencyChangeService){
        this.currencyService = currencyService;
        this.excelMaker = excelMaker;
        this.currencyChangeService = currencyChangeService;
    }

    @PostMapping(value = "/dane", produces = "application/octet-stream")
    public byte[] excelResponse(@RequestBody ReportPeriod reportPeriod) throws IOException, NoDataException{
        return excelMaker.generateExcel(reportPeriod);
    }

    @PostMapping(value = "/currency", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponse(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return excelMaker.generateExcelByCurrency(currencyReportPeriod, false);
    }


    @PostMapping(value = "/currency/extensions", produces = "application/octet-stream")
    public byte[] oneCurrencyExcelResponseWithExtensions(@RequestBody CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException{
        return excelMaker.generateExcelByCurrency(currencyReportPeriod, true);
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

    @PostMapping("/currency/change")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChange(@RequestBody
                                                                                                           FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyChangeService.findCurrencyWithHighestRatePercentageChange(
                findCurrencyWithHighestRatePercentageChangeRequest);
    }

    @PostMapping("/cos")
    public List<CurrencyFromStartDateAndEndDate> testowe(@RequestBody
                                                         FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyService.testowaMetoda(findCurrencyWithHighestRatePercentageChangeRequest.startDate(), findCurrencyWithHighestRatePercentageChangeRequest.endDate());
    }
}