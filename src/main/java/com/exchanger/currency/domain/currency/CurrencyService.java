package com.exchanger.currency.domain.currency;

import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public Page<LocalDate> availableDates(Pageable pageable){
        return currencyRepository.availableDates(pageable);
    }

    public Page<String> availableCodes(Pageable pageable){
        return currencyRepository.availableCodes(pageable);
    }


    public Page<LocalDate> availableDatesForCurrency(String code,Pageable pageable){
        return currencyRepository.availableDatesForCurrency(code,pageable);
    }

    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChange(
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        return FindCurrencyWithHighestRatePercentageChangeResponse.from(findCurrenciesFromStartDateAndEndDate(
                        findCurrencyWithHighestRatePercentageChangeRequest.startDate(),
                        findCurrencyWithHighestRatePercentageChangeRequest.endDate()),
                findCurrencyWithHighestRatePercentageChangeRequest.number());
    }

    private List<CurrencyFromStartDateAndEndDate> findCurrenciesFromStartDateAndEndDate(LocalDate startDate,
                                                                                        LocalDate endDate){
        return currencyRepository.findCurrencyFromStartDateAndEndDate(startDate, endDate);
    }
}