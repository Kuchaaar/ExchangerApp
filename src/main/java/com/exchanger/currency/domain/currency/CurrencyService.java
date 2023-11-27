package com.exchanger.currency.domain.currency;

import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    @Cacheable("availableDates")
    public List<LocalDate> availableDates(){
        return currencyRepository.availableDates();
    }

    @Cacheable("availableCodes")
    public List<String> availableCodes(){
        return currencyRepository.availableCodes();
    }

    @Cacheable("availableDatesForCurrency")
    public List<LocalDate> availableDatesForCurrency(String code){
        return currencyRepository.availableDatesForCurrency(code);
    }

    @Cacheable("findCurrencyWithHighestRatePercentageChange")
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChange(
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return FindCurrencyWithHighestRatePercentageChangeResponse.from(findCurrenciesFromStartDateAndEndDate(
                        findCurrencyWithHighestRatePercentageChangeRequest.startDate(),
                        findCurrencyWithHighestRatePercentageChangeRequest.endDate()),
                findCurrencyWithHighestRatePercentageChangeRequest.number());
    }

    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    @CacheEvict(cacheNames = {"availableDates",
            "availableCodes",
            "availableDatesForCurrency",
            "findCurrencyWithHighestRatePercentageChange"}, allEntries = true)
    public void clearCache(){
    }

    private List<CurrencyFromStartDateAndEndDate> findCurrenciesFromStartDateAndEndDate(LocalDate startDate,
                                                                                        LocalDate endDate){
        return currencyRepository.findCurrencyFromStartDateAndEndDate(startDate, endDate);
    }
}