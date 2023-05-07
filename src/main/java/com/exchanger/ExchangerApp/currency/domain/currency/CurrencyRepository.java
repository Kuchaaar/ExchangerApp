package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.domain.currency.Currency;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyResponse;

import java.util.List;

public interface CurrencyRepository {
    void saveAll(List<CurrencyResponse> list);

    List<Currency> findAll();
}
