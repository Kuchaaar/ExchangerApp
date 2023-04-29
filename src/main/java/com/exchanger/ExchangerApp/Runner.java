package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.CurrencyFetcher;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.CurrencyRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyFetcher currencyFetcher;
    private final CurrencyRepo currencyRepo;
    private final CurrencyClient currencyClient;

    public Runner(CurrencyFetcher currencyFetcher, CurrencyRepo currencyRepo, CurrencyClient currencyClient) {
        this.currencyFetcher = currencyFetcher;
        this.currencyRepo = currencyRepo;
        this.currencyClient = currencyClient;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        currencyFetcher.showAll();
        currencyRepo.saveAll(currencyClient.getByTable("a"));
    }
}
