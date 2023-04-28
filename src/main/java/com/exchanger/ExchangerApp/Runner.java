package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.CurrencyFetcher;
import com.exchanger.ExchangerApp.currency.persistance.Config;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyFetcher currencyFetcher;
    private final Config config;

    public Runner(CurrencyFetcher currencyFetcher, Config config) {
        this.currencyFetcher = currencyFetcher;
        this.config = config;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        currencyFetcher.showAll();
    }
}
