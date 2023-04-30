package com.exchanger.ExchangerApp.currency.domain;

import java.util.List;

public class CurrencyReader {
    private final CurrencyRepository currencyRepository;

    public CurrencyReader(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
