package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import org.springframework.stereotype.Component;

@Component
public class Sheduled {
    private final CurrencyClient currencyClient;
    private final DatabaseCurrencyRepository databaseCurrencyRepository;

    public Sheduled(CurrencyClient currencyClient, DatabaseCurrencyRepository databaseCurrencyRepository) {
        this.currencyClient = currencyClient;
        this.databaseCurrencyRepository = databaseCurrencyRepository;
    }
    public void SheduledUpdate(){
        CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient, databaseCurrencyRepository);
        databaseUpdater.update();
    }

}
