package com.exchanger.currency.persistence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "jpa"
)
public class DatabaseJPACurrencyRepository implements CurrencyRepository{
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
    public Page<String> availableCodes(Pageable pageable){
        return currencyRepositoryJPA.findDistinctByCode(pageable);
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
    public Page<LocalDate> availableDates(Pageable pageable){
        return currencyRepositoryJPA.findDistinctByDate(pageable);
    }

    @Override
    public List<Currency> findAll(){
        return currencyRepositoryJPA.findAll();
    }

    @Override public Page<LocalDate> availableDatesForCurrency(String code, Pageable pageable){
        return currencyRepositoryJPA.findDistinctByDateByCode(code, pageable);
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return currencyRepositoryJPA.findAllByCodeAndDateBetween(code, date1, date2);
    }

    @Override
    public List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate,
                                                                                     LocalDate endDate){
        return currencyRepositoryJPA.findCurrencyFromStartDateAndEndDate(startDate, endDate);
    }

    @Override
    public void deleteAll(){
        currencyRepositoryJPA.deleteAll();
    }
}