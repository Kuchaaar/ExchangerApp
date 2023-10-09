package com.exchanger.currency.domain.JPAcurrency;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class StringIntoCurrency {
    public CurrencyJPA stringIntoCurrency(String data) {
        CurrencyJPA currencyJPA = new CurrencyJPA();
        String[] params = data.split("&");

        Map<String, Consumer<String>> propertySetters = new HashMap<>();
        propertySetters.put("id", value -> currencyJPA.setId(Long.parseLong(value)));
        propertySetters.put("name", currencyJPA::setCurrencyName);
        propertySetters.put("code", currencyJPA::setCurrencyCode);
        propertySetters.put("rate", value -> currencyJPA.setExchangeRate(Double.parseDouble(value)));
        propertySetters.put("date", currencyJPA::setDate);

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                propertySetters.getOrDefault(key, ignored -> {
                }).accept(value);
            }
        }

        return currencyJPA;
    }
}