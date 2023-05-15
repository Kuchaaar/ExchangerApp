package com.exchanger;

import com.exchanger.currency.domain.currency.*;
import com.exchanger.currency.domain.holidays.HolidaysReader;
import com.exchanger.currency.domain.holidays.HolidaysUpdater;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableScheduling
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyUpdater currencyUpdater;
    private final DateChecker dateChecker;
    private final CurrencyRepository currencyRepository;
    private final HolidaysUpdater holidaysUpdater;
    private final HolidaysReader holidaysReader;
    private final MainClass mainClass;
    public Runner(CurrencyUpdater currencyUpdater, DateChecker dateChecker, CurrencyRepository currencyRepository,
                  HolidaysUpdater holidaysUpdater, HolidaysReader holidaysReader, MainClass mainClass) {
        this.currencyUpdater = currencyUpdater;
        this.dateChecker = dateChecker;
        this.currencyRepository = currencyRepository;
        this.holidaysUpdater = holidaysUpdater;
        this.holidaysReader = holidaysReader;
        this.mainClass = mainClass;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        System.out.println(currencyReader2.writeData());
//        System.out.println(currencyRepository.availableDate());
//        System.out.println(currencyRepository.findByDate("2023-05-11"));
//        holidaysUpdater.update();
//        currencyUpdater.update("a",2);
//        currencyUpdater.update("a");
//        System.out.println(availableDate.findAll());
//        System.out.println(currencyReader.findAll() );
//        currencyUpdater.update("b");
//        System.out.println(currencyReader.findAll() );
//        System.out.println(holidaysReader.findHolidaysByYear());
//        mainClass.ExtractData("a",3);
//        System.out.println(mainClass.ExtractData(LocalDate.now(),LocalDate.now().minusDays(5)));
        System.out.println(currencyRepository.findByDates(LocalDate.now().minusDays(3),LocalDate.now()));



    }
}
