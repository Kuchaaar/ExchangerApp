package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.currency.*;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysRepository;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysUpdater;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.holidays.HolidaysClient;
import com.exchanger.ExchangerApp.currency.peristence.holidays.DatabaseHolidaysRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableScheduling
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyClient currencyClient;
    private final CurrencyRepository currencyRepository;
    private final HolidaysClient holidaysClient;
    private final HolidaysRepository holidaysRepository;
    private final LocalDate currentDate = LocalDate.now();
    private final Sheduled sheduled;
    private final DateChecker dateChecker;
    private final MainClass mainClass;
    @Bean
    public CurrencySheduler myScheduler(HolidaysRepository holidaysRepository,
                                        Sheduled sheduled, HolidaysClient holidaysClient, DateChecker dateChecker) {
        return new CurrencySheduler(holidaysRepository,sheduled,holidaysClient,dateChecker);
    }
    public Runner(CurrencyClient currencyClient,
                  CurrencyRepository currencyRepository, HolidaysClient holidaysClient,
                  HolidaysRepository holidaysRepository,
                  Sheduled sheduled, DateChecker dateChecker, MainClass mainClass) {
        this.currencyClient = currencyClient;
        this.currencyRepository = currencyRepository;
        this.holidaysClient = holidaysClient;
        this.holidaysRepository = holidaysRepository;
        this.sheduled = sheduled;
        this.dateChecker = dateChecker;
        this.mainClass = mainClass;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient, currencyRepository);
        databaseUpdater.update("a");
        databaseUpdater.update("b");
//        CurrencyReader databaseCurrencyReader = new CurrencyReader(currencyRepository);
//        CurrencyReader inMemoryCurrencyReader = new CurrencyReader(currencyRepository);
//        System.out.println(inMemoryCurrencyReader.findAll() );
        HolidaysUpdater databaseHolidaysUpdater = new HolidaysUpdater(holidaysClient,holidaysRepository);
        databaseHolidaysUpdater.update();
//        HolidaysReader databaseHolidaysReader = new HolidaysReader(holidaysRepository);
//        HolidaysReader inMemoryHolidaysReader = new HolidaysReader(holidaysRepository);
//        System.out.println(inMemoryHolidaysReader.findHolidaysByYear());
//        System.out.println(databaseHolidaysReader.findHolidaysByYear());
//        System.out.println(currencyClient.getByTable("a"));
//        mainClass.ExtractData("a",3);



    }
}
