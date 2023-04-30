package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;

import java.util.List;

public interface CurrencyRepository {
    void saveAll(List<CurrencyResponse> list);

    List<Currency> findAll();
}
