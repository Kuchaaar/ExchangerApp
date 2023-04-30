package com.exchanger.ExchangerApp.currency.integration;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class CurrencyRepo implements CurrencyMap {
    public final Map<String, Currency>  currencyMap = new HashMap<>();
    @Override
    public void saveAll(List<CurrenciesResponse> list) {
        list.stream()
                .map(CurrenciesResponse::rates)
                .flatMap(Collection::stream)
                .forEach(currency -> currencyMap.put(currency.code(), currency));
    }
}
