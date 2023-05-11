package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;

import java.util.List;

public interface CurrencyRepository {
    void saveAll(List<CurrencyResponse> list);

    List<Currency> findAll();
}
