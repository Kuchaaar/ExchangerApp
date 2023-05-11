package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class MainClass {
    private DateChecker dateChecker;
    private final CurrencyReader2 currencyReader2;
    private final CurrencyUpdater currencyUpdater;

    public MainClass(DateChecker dateChecker,
                     CurrencyUpdater currencyUpdater,
                     CurrencyReader2 currencyReader2) {
        this.dateChecker = dateChecker;
        this.currencyUpdater = currencyUpdater;
        this.currencyReader2 = currencyReader2;
    }

    public void ExtractData(String table , int topCount){
        if(!dateChecker.ifInDatabase(LocalDate.now())){
            currencyUpdater.update(table,topCount);
            currencyUpdater.update(table);
            currencyReader2.WriteData(topCount);
        }
        else{
            currencyReader2.WriteData(topCount);
        }
    }


//    public void ExtractData(String table){
//        if(!dateChecker.ifInDatabase()){
//            databaseUpdater.update(table);
//            inMemoryDatabaseUpdater.update(table);
//            currencyReader2.WriteData();
//        }
//        else{
//            currencyReader2.WriteData();
//        }
//    }
}
