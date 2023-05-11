package com.exchanger.ExchangerApp.currency.domain.currency;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CurrencyReader {
    private final CurrencyRepository currencyRepository;

    public CurrencyReader(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
