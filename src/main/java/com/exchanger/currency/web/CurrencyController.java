package com.exchanger.currency.web;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Cacheable("currenciesByDate")
    @GetMapping("/currency/{code}")
    public List<Currency> getRates(
            @PathVariable String code,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return currencyService.findCurrencyByDates(startDate, endDate, code);
    }

    @Cacheable("availableDates")
    @GetMapping("/available_dates")
    public List<LocalDate> getLocalDates() {
        return currencyService.availableDates();
    }

    @Cacheable("availableCodes")
    @GetMapping("/available_codes")
    public List<String> getCurrencyCodes() {
        return currencyService.availableCodes();
    }


    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    @CacheEvict(cacheNames = {"availableDates",
            "availableCodes",
            "availableDatesForCurrency", "currenciesByDate"}, allEntries = true)
    public void clearCache() {
    }
}