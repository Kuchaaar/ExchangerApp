package com.exchanger.currency.domain.currency;

import com.exchanger.currency.domain.holidays.HolidaysUpdater;
import com.exchanger.currency.integration.currency.CurrencyClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledUpdated {

    private final HolidaysUpdater holidaysUpdater;
    private final CurrencyUpdater currencyUpdater;
    private final CurrencyClient currencyClient;

    private final DatabaseChecker databaseChecker;

    public ScheduledUpdated(HolidaysUpdater holidaysUpdater, CurrencyUpdater currencyUpdater
            , CurrencyClient currencyClient, DatabaseChecker databaseChecker) {
        this.currencyUpdater = currencyUpdater;
        this.holidaysUpdater = holidaysUpdater;
        this.currencyClient = currencyClient;
        this.databaseChecker = databaseChecker;
    }

    public void currencyUpdate() {
        if (databaseChecker.ifDataInDatabase(currencyClient.getByTable("a"))) {
            currencyUpdater.update("a");
        }
        if (databaseChecker.ifDataInDatabase(currencyClient.getByTable("b"))) {
            currencyUpdater.update("b");
        }
    }

    public void holidaysUpdate() {
        holidaysUpdater.deleteAll();
        int year = LocalDate.now().getYear();
        holidaysUpdater.update(year);
    }

}