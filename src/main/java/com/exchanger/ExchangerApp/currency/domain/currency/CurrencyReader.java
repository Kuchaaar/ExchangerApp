package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.peristence.currency.DatabaseCurrencyRepository;
import com.exchanger.ExchangerApp.currency.peristence.currency.InMemoryCurrencyRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
