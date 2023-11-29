package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @GetMapping("/available_dates")
    public List<LocalDate> getLocalDates(){
        return currencyService.availableDates();
    }

    @GetMapping("/available_codes")
    public List<String> getCurrencyCodes(){
        return currencyService.availableCodes();
    }

    @PostMapping("/available_dates/currency")
    public List<LocalDate> findLocalDatesForCurrency(@RequestBody String currencyCode){
        return currencyService.availableDatesForCurrency(currencyCode);
    }

    @PostMapping("/currency/highest_rate_percentage_change")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponse(
            @RequestBody
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyService.findCurrencyWithHighestRatePercentageChange(
                findCurrencyWithHighestRatePercentageChangeRequest);
    }
}