package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.Holidays.*;
import com.exchanger.ExchangerApp.currency.domain.*;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import com.exchanger.ExchangerApp.currency.peristence.InMemoryCurrencyRepository;
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
    private final InMemoryCurrencyRepository inMemoryCurrencyRepository;
    private final DatabaseCurrencyRepository databaseCurrencyRepository;
    private final HolidaysClient holidaysClient;
    private final InMemoryHolidaysRepository inMemoryHolidaysRepository;
    private final DatabaseHolidaysRepository databaseHolidaysRepository;
    private final LocalDate currentDate = LocalDate.now();
    private final Sheduled sheduled;
    private final DateChecker dateChecker;
    @Bean
    public CurrencySheduler myScheduler(InMemoryHolidaysRepository inMemoryHolidaysRepository,
                                        Sheduled sheduled, HolidaysClient holidaysClient, DateChecker dateChecker) {
        return new CurrencySheduler(inMemoryHolidaysRepository,sheduled,holidaysClient,dateChecker);
    }
    public Runner(CurrencyClient currencyClient,
                  InMemoryCurrencyRepository inMemoryCurrencyRepository,
                  DatabaseCurrencyRepository databaseCurrencyRepository, HolidaysClient holidaysClient, InMemoryHolidaysRepository inMemoryHolidaysRepository, DatabaseHolidaysRepository databaseHolidaysRepository, Sheduled sheduled, DateChecker dateChecker) {
        this.currencyClient = currencyClient;
        this.inMemoryCurrencyRepository = inMemoryCurrencyRepository;
        this.databaseCurrencyRepository = databaseCurrencyRepository;
        this.holidaysClient = holidaysClient;
        this.inMemoryHolidaysRepository = inMemoryHolidaysRepository;
        this.databaseHolidaysRepository = databaseHolidaysRepository;
        this.sheduled = sheduled;
        this.dateChecker = dateChecker;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient, databaseCurrencyRepository);
        CurrencyUpdater inMemoryDatabaseUpdater = new CurrencyUpdater(currencyClient, inMemoryCurrencyRepository);
//        databaseUpdater.update();
//        inMemoryDatabaseUpdater.update();
//        CurrencyReader databaseCurrencyReader = new CurrencyReader(databaseCurrencyRepository);
//        CurrencyReader inMemoryCurrencyReader = new CurrencyReader(inMemoryCurrencyRepository);
//        System.out.println(inMemoryCurrencyReader.findAll() );
//        HolidaysUpdater databaseHolidaysUpdater = new HolidaysUpdater(holidaysClient,databaseHolidaysRepository);
//        HolidaysUpdater inMemoryHolidaysUpdater = new HolidaysUpdater(holidaysClient,inMemoryHolidaysRepository);
//        databaseHolidaysUpdater.update();
//        inMemoryHolidaysUpdater.update();
//        HolidaysReader databaseHolidaysReader = new HolidaysReader(databaseHolidaysRepository);
//        HolidaysReader inMemoryHolidaysReader = new HolidaysReader(inMemoryHolidaysRepository);
//        System.out.println(inMemoryHolidaysReader.findHolidays());
//        System.out.println(currencyClient.getByTable("a"));
        System.out.println(dateChecker.ifInDatabase(3));
        System.out.println(dateChecker.ifInDatabase());


    }
}
