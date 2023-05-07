package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.domain.currency.CurrencyReader;
import com.exchanger.ExchangerApp.currency.domain.currency.CurrencyReader2;
import com.exchanger.ExchangerApp.currency.domain.currency.CurrencyUpdater;
import com.exchanger.ExchangerApp.currency.domain.currency.DateChecker;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.currency.DatabaseCurrencyRepository;
import com.exchanger.ExchangerApp.currency.peristence.currency.InMemoryCurrencyRepository;
import org.springframework.stereotype.Component;



@Component
public class MainClass {
    private DateChecker dateChecker;
    private DatabaseCurrencyRepository databaseCurrencyRepository;
    private InMemoryCurrencyRepository inMemoryCurrencyRepository;
    private CurrencyClient currencyClient;
    private CurrencyReader2 currencyReader2;

    private CurrencyReader databaseCurrencyReader = new CurrencyReader(databaseCurrencyRepository);
    private CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient, databaseCurrencyRepository);
    private CurrencyUpdater inMemoryDatabaseUpdater = new CurrencyUpdater(currencyClient, inMemoryCurrencyRepository);

    public MainClass(DateChecker dateChecker, DatabaseCurrencyRepository databaseCurrencyRepository,
                     InMemoryCurrencyRepository inMemoryCurrencyRepository, CurrencyClient currencyClient,
                     CurrencyReader2 currencyReader2) {
        this.dateChecker = dateChecker;
        this.databaseCurrencyRepository = databaseCurrencyRepository;
        this.inMemoryCurrencyRepository = inMemoryCurrencyRepository;
        this.currencyClient = currencyClient;
        this.currencyReader2 = currencyReader2;
    }

    public void ExtractData(String table , int topCount){
        if(!dateChecker.ifInDatabase(topCount)){
            databaseUpdater.update(table,topCount);
            inMemoryDatabaseUpdater.update(table);
            currencyReader2.WriteData(topCount);
        }
        else{
            currencyReader2.WriteData(topCount);
        }
    }


    public void ExtractData(String table){
        if(!dateChecker.ifInDatabase()){
            databaseUpdater.update(table);
            inMemoryDatabaseUpdater.update(table);
            currencyReader2.WriteData();
        }
        else{
            currencyReader2.WriteData();
        }
    }
}