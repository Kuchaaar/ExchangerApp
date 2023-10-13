package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "memory"
)
public class InMemoryCurrencyRepository implements CurrencyRepository {
    private final List<Currency> currencies = new ArrayList<>();


    @Override
    public void saveAll(List<CurrencyResponse> list){
        list.forEach(currencyResponse -> currencies.add(Currency.from(currencyResponse)));
    }

    @Override
    public List<String> availableCodes(){
        return currencies.stream()
                .map(Currency::getCode)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return currencies.stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .toList();
    }

    @Override
    public List<Currency> findByDate(LocalDate date){
        return currencies.stream()
                .filter(currency -> currency.getDate().equals(date))
                .toList();
    }

    @Override
    public List<LocalDate> availableDates(){
        return currencies.stream()
                .map(Currency::getDate)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findAll(){
        return currencies
                .stream()
                .toList();
    }

    @Override
    public List<LocalDate> availableDatesForCurrency(String code){
        return currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .map(Currency::getDate)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return currencies.stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .filter(currency -> currency.getCode().equals(code))
                .toList();
    }

}