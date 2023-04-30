package com.exchanger.ExchangerApp.currency.integration;

import java.util.List;

public interface CurrencyMap {
    void saveAll(List<CurrenciesResponse> list);
}
