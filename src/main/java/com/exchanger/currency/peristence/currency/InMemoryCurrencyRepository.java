package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(
        value = "repository.mock.currency.mock.enable",
        havingValue = "true"
)
public class InMemoryCurrencyRepository implements CurrencyRepository {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    

    @Override
    public void saveAll(List<CurrencyResponse> list) {
        list.forEach(currencyResponse -> currencyMap.put(currencyResponse.code(),
                Currency.from(currencyResponse)));
    }

    @Override
    public List<Currency> findByDates(String date1, String date2) {
        List<Currency> result = new ArrayList<>();
        for (Currency currency : currencyMap.values()) {
            if (currency.date().equals(date1) || currency.date().equals(date2)) {
                result.add(currency);
            }
        }
        return result;
    }

    @Override
    public List<Currency> findByDate(String date) {
        List<Currency> result = new ArrayList<>();
        for (Currency currency : currencyMap.values()) {
            if (currency.date().equals(date)) {
                result.add(currency);
            }
        }
        return result;
    }

    @Override
    public List<String> availableDate() {
        return getDistinctDates(currencyMap);
    }

    @Override
    public List<Currency> findAll() {
        return currencyMap.values()
                .stream()
                .toList();
    }
    private static List<String> getDistinctDates(Map<String, Currency> currencyMap) {
        Set<String> dateSet = new HashSet<>(currencyMap.keySet());
        return new ArrayList<>(dateSet);
    }
}
