package com.exchanger.currency.domain.JPAcurrency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }
    public void saveAll(List<CurrencyResponse> list){
        currencyRepository.saveAll(list);
    }

    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return currencyRepository.findByDates(date1,date2);
    }

    public List<Currency> findByDate(LocalDate date){
        return currencyRepository.findByDate(date);
    }

    public List<String> availableDates(){
        return currencyRepository.availableDates();
    }

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }

    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return currencyRepository.findCurrencyByDates(date1,date2,code);
    }
}
