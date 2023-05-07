package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyClient;
import org.springframework.stereotype.Component;



@Component
public class MainClass {
    private DateChecker dateChecker;
    private CurrencyRepository currencyRepository;
    private CurrencyClient currencyClient;
    private CurrencyReader2 currencyReader2;

    private CurrencyReader databaseCurrencyReader = new CurrencyReader(currencyRepository);
    private CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient, currencyRepository);
    private CurrencyUpdater inMemoryDatabaseUpdater = new CurrencyUpdater(currencyClient, currencyRepository);

    public MainClass(DateChecker dateChecker, CurrencyRepository currencyRepository,
                     CurrencyClient currencyClient,
                     CurrencyReader2 currencyReader2) {
        this.dateChecker = dateChecker;
        this.currencyRepository = currencyRepository;
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
