package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

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
    public Page<LocalDate> getLocalDates(Pageable pageable){
        return currencyService.availableDates(pageable);
    }

    @Cacheable("availableCodes")
    @GetMapping("/available_codes")
    public Page<String> getCurrencyCodes(Pageable pageable){
        return currencyService.availableCodes(pageable);
    }
    @Cacheable("availableDatesForCurrency")
    @GetMapping("/available_dates/currency")
    public Page<LocalDate> findLocalDatesForCurrency(@RequestParam String currencyCode,Pageable pageable){
        return currencyService.availableDatesForCurrency(currencyCode,pageable);
    }

    @PostMapping("/currency/highest_rate_percentage_change")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponse(
            @RequestBody
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return currencyService.findCurrencyWithHighestRatePercentageChange(
                findCurrencyWithHighestRatePercentageChangeRequest);
    }
    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    @CacheEvict(cacheNames = {"availableDates",
            "availableCodes",
            "availableDatesForCurrency"}, allEntries = true)
    public void clearCache(){
    }
}