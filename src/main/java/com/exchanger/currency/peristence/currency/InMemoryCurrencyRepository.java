package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "memory"
)
public class InMemoryCurrencyRepository implements CurrencyRepository {
    private final Map<String, Currency> currencyMap = new HashMap<>();


    @Override
    public void saveAll(List<CurrencyResponse> list){
        list.forEach(currencyResponse -> currencyMap.put(currencyResponse.code(),
                Currency.from(currencyResponse)));
    }

    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return currencyMap.values().stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .toList();
    }

    @Override
    public List<Currency> findByDate(LocalDate date){
        return currencyMap.values().stream()
                .filter(currency -> currency.getDate().equals(date))
                .toList();
    }

    @Override
    public List<LocalDate> availableDates(){
        return currencyMap.values().stream()
                .map(Currency::getDate)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findAll(){
        return currencyMap.values()
                .stream()
                .toList();
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return currencyMap.values().stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .filter(currency -> currency.getCode().equals(code))
                .toList();
    }
}