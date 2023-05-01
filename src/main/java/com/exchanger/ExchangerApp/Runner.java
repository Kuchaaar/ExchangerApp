package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.CurrencyReader;
import com.exchanger.ExchangerApp.currency.domain.CurrencyUpdater;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import com.exchanger.ExchangerApp.currency.peristence.InMemoryCurrencyRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyClient currencyClient;
    private final InMemoryCurrencyRepository inMemoryCurrencyRepository;
    private final DatabaseCurrencyRepository databaseCurrencyRepository;

    public Runner(CurrencyClient currencyClient,
                  InMemoryCurrencyRepository inMemoryCurrencyRepository,
                  DatabaseCurrencyRepository databaseCurrencyRepository) {
        this.currencyClient = currencyClient;
        this.inMemoryCurrencyRepository = inMemoryCurrencyRepository;
        this.databaseCurrencyRepository = databaseCurrencyRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient, databaseCurrencyRepository);
        CurrencyUpdater inMemoryDatabaseUpdater = new CurrencyUpdater(currencyClient, inMemoryCurrencyRepository);
        databaseUpdater.update();
        inMemoryDatabaseUpdater.update();
        CurrencyReader databaseCurrencyReader = new CurrencyReader(databaseCurrencyRepository);
        CurrencyReader inMemoryCurrencyReader = new CurrencyReader(inMemoryCurrencyRepository);
        System.out.println(inMemoryCurrencyReader.findAll() );

    }
}
