package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrenciesResponse;

import java.time.LocalDate;
import java.util.List;

public class DatabaseChecker {
    private final CurrencyRepository currencyRepository;

    public DatabaseChecker(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    public boolean ifDataInDatabase(List<CurrenciesResponse> currenciesResponse){
        LocalDate date = LocalDate.parse(currenciesResponse.get(1).effectiveDate());
        List<Currency> list = currencyRepository.findByDate(date);
        return !list.isEmpty();
    }
}
