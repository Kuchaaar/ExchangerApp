package com.exchanger.currency.services.sheduling;

import com.exchanger.currency.domain.currency.CurrencyUpdater;
import com.exchanger.currency.domain.holidays.HolidaysUpdater;
import com.exchanger.currency.integration.currency.CurrencyClient;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledUpdated {

    private final HolidaysUpdater holidaysUpdater;
    private final CurrencyUpdater currencyUpdater;
    private final CurrencyClient currencyClient;

    private final DatabaseChecker databaseChecker;

    public ScheduledUpdated(HolidaysUpdater holidaysUpdater, CurrencyUpdater currencyUpdater
            , CurrencyClient currencyClient, DatabaseChecker databaseChecker){
        this.currencyUpdater = currencyUpdater;
        this.holidaysUpdater = holidaysUpdater;
        this.currencyClient = currencyClient;
        this.databaseChecker = databaseChecker;
    }

    public void currencyUpdate(){
        if(!databaseChecker.ifDataInDatabase(currencyClient.getByTable("a"))){
            currencyUpdater.update("a");
        }
        if(!databaseChecker.ifDataInDatabase(currencyClient.getByTable("b"))){
            currencyUpdater.update("b");
        }
    }

    public void currencyUpdate(LocalDate startDate, LocalDate endDate){
        safeGetData("a",startDate,endDate);
        safeGetData("b",startDate,endDate);
    }
    private void safeGetData(String table,LocalDate startDate, LocalDate endDate){
        try {
            currencyUpdater.update(table, startDate, endDate);
        }catch(FeignException e){
            System.out.println(e.getMessage());
        }
    }

    public void holidaysUpdate(){
        holidaysUpdater.deleteAll();
        int year = LocalDate.now().getYear();
        holidaysUpdater.update(year);
    }
}