package com.exchanger.currency.services.sheduling;

import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrenciesResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseChecker {

    private final CurrencyRepository currencyRepository;

    public DatabaseChecker(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public boolean ifDataInDatabase(List<CurrenciesResponse> currenciesResponse){
        return ifDateInDatabase(LocalDate.parse(currenciesResponse.get(0).effectiveDate()));
    }

    public boolean ifDateInDatabase(LocalDate actualizationDate){
        return currencyRepository.isDateInData(actualizationDate);
    }
}