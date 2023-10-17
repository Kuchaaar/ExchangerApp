package com.exchanger.currency.domain.currency;

import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public List<LocalDate> availableDates(){
        return currencyRepository.availableDates();
    }

    public List<String> availableCodes(){
        return currencyRepository.availableCodes();
    }
    public List<LocalDate> avilableDatesForCurrency(String code){
        return currencyRepository.availableDatesForCurrency(code);
    }
    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChange(
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return FindCurrencyWithHighestRatePercentageChangeResponse.from(findCurrenciesFromStartDateAndEndDate(findCurrencyWithHighestRatePercentageChangeRequest.startDate(),
                findCurrencyWithHighestRatePercentageChangeRequest.endDate()),findCurrencyWithHighestRatePercentageChangeRequest.number());
    }
    private List<CurrencyFromStartDateAndEndDate> findCurrenciesFromStartDateAndEndDate(LocalDate startDate, LocalDate endDate){
        return currencyRepository.findCurrencyFromStartDateAndEndDate(startDate,endDate);
    }
}