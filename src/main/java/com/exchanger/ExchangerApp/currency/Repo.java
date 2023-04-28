package com.exchanger.ExchangerApp.currency;

import com.exchanger.ExchangerApp.currency.integration.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    public Map<String, Currency> saveAll(List<CurrenciesResponse> list) {
        Map<String, Currency> currencyMap = new HashMap<>();
        for (CurrenciesResponse currenciesResponse : list) {
            List<Currency> currencyList = currenciesResponse.rates();
            for (Currency currency : currencyList) {
                currencyMap.put(currency.code(), currency);
            }
        }
        return currencyMap;
    }
}
