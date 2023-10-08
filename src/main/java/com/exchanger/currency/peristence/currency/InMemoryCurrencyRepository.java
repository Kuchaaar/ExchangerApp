package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    public List<Currency> findByDates(LocalDate date1, LocalDate date2) {
        return currencyMap.values().stream()
                .filter(currency -> currency.date().isAfter(date1) && currency.date().isBefore(date2))
                .toList();
    }

    @Override
    public List<Currency> findByDate(LocalDate date) {
        return currencyMap.values().stream()
                .filter(currency -> currency.date().equals(date))
                .toList();
    }

    @Override
    public List<String> availableDates() {
        return new ArrayList<>(currencyMap.keySet());
    }

    @Override
    public List<Currency> findAll() {
        return currencyMap.values()
                .stream()
                .toList();
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code) {
        return currencyMap.values().stream()
                .filter(currency -> currency.date().isAfter(date1) && currency.date().isBefore(date2))
                .filter(currency -> currency.code().equals(code))
                .toList();
    }
}