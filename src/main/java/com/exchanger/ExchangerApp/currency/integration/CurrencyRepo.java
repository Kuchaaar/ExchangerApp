package com.exchanger.ExchangerApp.currency.integration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyRepo {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    public void saveAll(List<CurrenciesResponse> list) {
        list.stream()
                .map(CurrenciesResponse::rates)
                .flatMap(Collection::stream)
                .forEach(currency -> currencyMap.put(currency.code(), currency));
    }
}
