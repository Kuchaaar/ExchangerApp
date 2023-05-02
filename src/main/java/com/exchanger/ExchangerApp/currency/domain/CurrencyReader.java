package com.exchanger.ExchangerApp.currency.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CurrencyReader {
    private final CurrencyRepository currencyRepository;

    public CurrencyReader(@Qualifier("InMemoryCurrencyRepository") CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
