package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.currency.*;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysReader;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysUpdater;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyUpdater currencyUpdater;
    private final CurrencyReader currencyReader;
    private final HolidaysUpdater holidaysUpdater;
    private final HolidaysReader holidaysReader;

    public Runner(CurrencyUpdater currencyUpdater, CurrencyReader currencyReader,
                  HolidaysUpdater holidaysUpdater, HolidaysReader holidaysReader) {
        this.currencyUpdater = currencyUpdater;
        this.currencyReader = currencyReader;
        this.holidaysUpdater = holidaysUpdater;
        this.holidaysReader = holidaysReader;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        holidaysUpdater.update();
//        currencyUpdater.update("a",2);
//        currencyUpdater.update("a");
//        System.out.println(currencyReader.findAll() );
//        currencyUpdater.update("b");
//        System.out.println(currencyReader.findAll() );
//        System.out.println(holidaysReader.findHolidaysByYear());
//       mainClass.ExtractData("a",3);



    }
}
