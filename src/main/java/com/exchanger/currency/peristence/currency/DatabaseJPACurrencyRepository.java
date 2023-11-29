package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "jpa"
)
public class DatabaseJPACurrencyRepository implements CurrencyRepository {
    private final CurrencyRepositoryJPA currencyRepositoryJPA;

    @Autowired
    public DatabaseJPACurrencyRepository(CurrencyRepositoryJPA currencyRepositoryJPA){
        this.currencyRepositoryJPA = currencyRepositoryJPA;
    }

    @Override
    public void saveAll(List<CurrencyResponse> currenciesResponse){
        List<Currency> currencies = currenciesResponse.stream()
                .map(Currency::from)
                .toList();
        currencyRepositoryJPA.saveAll(currencies);
    }

    @Override
    public List<String> availableCodes(){
        return currencyRepositoryJPA.findDistinctByCode();
    }

    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return currencyRepositoryJPA.findByDateBetween(date1, date2);
    }

    @Override
    public boolean isDateInData(LocalDate date){
        return currencyRepositoryJPA.isDateInData(date);
    }

    @Override
    public List<LocalDate> availableDates(){
        return currencyRepositoryJPA.findDistinctByDate();
    }

    @Override
    public List<Currency> findAll(){
        return currencyRepositoryJPA.findAll();
    }

    @Override public List<LocalDate> availableDatesForCurrency(String code){
        return currencyRepositoryJPA.findDistinctByDateByCode(code);
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return currencyRepositoryJPA.findAllByCodeAndDateBetween(code, date1, date2);
    }

    @Override
    public List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate,
                                                                                     LocalDate endDate){
        return currencyRepositoryJPA.findCurrencyFromStartDateAndEndDate(startDate,endDate);
    }
}