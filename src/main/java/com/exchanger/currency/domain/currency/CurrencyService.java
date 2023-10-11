package com.exchanger.currency.domain.currency;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public List<String> availableDates(){
        return currencyRepository.availableDates();
    }

}
