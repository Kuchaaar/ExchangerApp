package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrenciesResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseChecker {

    private final CurrencyRepository currencyRepository;

    public DatabaseChecker(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public boolean ifDataInDatabase(List<CurrenciesResponse> currenciesResponse) {
        if (currenciesResponse.size() > 1) {
            LocalDate date = LocalDate.parse(currenciesResponse.get(1).effectiveDate());
            List<Currency> list = currencyRepository.findByDate(date);
            return !list.isEmpty();
        } else {
            return true;
        }
    }

    public boolean ifDateInDatabase(LocalDate actualizationDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", actualizationDate);
        List<Currency> list = currencyRepository.findByDate(actualizationDate);
        return !list.isEmpty();
    }
}