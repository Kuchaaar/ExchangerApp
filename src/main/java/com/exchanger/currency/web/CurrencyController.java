package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Cacheable("availableDates")
    @GetMapping("/available_dates")
    public List<LocalDate> getLocalDates(){
        return currencyService.availableDates();
    }

    @Cacheable("availableCodes")
    @GetMapping("/available_codes")
    public List<String> getCurrencyCodes(){
        return currencyService.availableCodes();
    }
    @Cacheable("availableDatesForCurrency")
    @PostMapping("/available_dates/currency")
    public List<LocalDate> findLocalDatesForCurrency(@RequestBody String currencyCode){
        return currencyService.availableDatesForCurrency(currencyCode);
    }

    @Cacheable("findCurrencyWithHighestRatePercentageChange")
    @PostMapping("/currency/highest_rate_percentage_change")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponse(
            @RequestBody
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyService.findCurrencyWithHighestRatePercentageChange(
                findCurrencyWithHighestRatePercentageChangeRequest);
    }
    @Scheduled(cron = "0 0 22 ? * MON-FRI") // zmiana w bazie nie sztywne godziny
    @CacheEvict(cacheNames = {"availableDates",
            "availableCodes",
            "availableDatesForCurrency",
            "findCurrencyWithHighestRatePercentageChange"}, allEntries = true)
    public void clearCache(){}
}