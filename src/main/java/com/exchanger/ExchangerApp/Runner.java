package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.CurrencyFetcher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyFetcher currencyFetcher;

    public Runner(CurrencyFetcher currencyFetcher) {
        this.currencyFetcher = currencyFetcher;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        currencyFetcher.showAll();
    }
}
