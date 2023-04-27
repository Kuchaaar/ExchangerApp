package com.exchanger.ExchangerApp.currency.domain;


import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import org.springframework.stereotype.Component;

@Component
public class CurrencyFetcher {

    private final CurrencyClient currencyClient;

    public CurrencyFetcher(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    public void showAll() {
        currencyClient.getByTable("a").forEach(System.out::println);
    }
}


