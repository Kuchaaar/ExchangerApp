package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.Holidays.*;
import com.exchanger.ExchangerApp.currency.domain.CurrencyReader;
import com.exchanger.ExchangerApp.currency.domain.CurrencyUpdater;
import com.exchanger.ExchangerApp.currency.domain.ShedulerTest;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import com.exchanger.ExchangerApp.currency.peristence.InMemoryCurrencyRepository;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Bean
    public ShedulerTest myScheduler(@Qualifier("InMemoryHolidaysRepository") HolidaysRepository holidaysRepository) {
        return new ShedulerTest(holidaysRepository);
    }
    public Runner(CurrencyClient currencyClient,
                  InMemoryCurrencyRepository inMemoryCurrencyRepository,
                  DatabaseCurrencyRepository databaseCurrencyRepository, HolidaysClient holidaysClient, InMemoryHolidaysRepository inMemoryHolidaysRepository, DatabaseHolidaysRepository databaseHolidaysRepository) {
        this.currencyClient = currencyClient;
        this.inMemoryCurrencyRepository = inMemoryCurrencyRepository;
        this.databaseCurrencyRepository = databaseCurrencyRepository;
        this.holidaysClient = holidaysClient;
        this.inMemoryHolidaysRepository = inMemoryHolidaysRepository;
        this.databaseHolidaysRepository = databaseHolidaysRepository;
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
        HolidaysUpdater databaseHolidaysUpdater = new HolidaysUpdater(holidaysClient,databaseHolidaysRepository);
        HolidaysUpdater inMemoryHolidaysUpdater = new HolidaysUpdater(holidaysClient,inMemoryHolidaysRepository);
        databaseHolidaysUpdater.update();
        inMemoryHolidaysUpdater.update();
        HolidaysReader databaseHolidaysReader = new HolidaysReader(databaseHolidaysRepository);
        HolidaysReader inMemoryHolidaysReader = new HolidaysReader(inMemoryHolidaysRepository);
        System.out.println(inMemoryHolidaysReader.findHolidays());


    }
}
