package com.exchanger.ExchangerApp.currency.integration;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class CurrencyRepo implements CurrencyMap {
    public final Map<String, List<Currency>>  currencyMap = new HashMap<>();
    @Override
    public void saveAll(List<CurrenciesResponse> list) {
        list.stream()
                .map(CurrenciesResponse::rates)
                .flatMap(Collection::stream)
                .forEach(currency -> {
                    List<Currency> currencyList = currencyMap.get(currency.code());
                    if (currencyList == null) {
                        currencyList = new ArrayList<>();
                        currencyMap.put(currency.code(), currencyList);
                    }
                    currencyList.add(currency);
                });
    }
}
