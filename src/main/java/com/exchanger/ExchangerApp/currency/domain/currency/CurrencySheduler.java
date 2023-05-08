package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysReader;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysRepository;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysUpdater;
import com.exchanger.ExchangerApp.currency.integration.holidays.HolidaysClient;
import com.exchanger.ExchangerApp.currency.peristence.holidays.InMemoryHolidaysRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CurrencySheduler {
private final HolidaysRepository holidaysRepository;
private final Sheduled sheduled;
private final HolidaysClient holidaysClient;
private final DateChecker dateChecker;

    public CurrencySheduler(HolidaysRepository holidaysRepository,
                            Sheduled sheduled,
                            HolidaysClient holidaysClient,
                            DateChecker dateChecker) {
        this.holidaysRepository = holidaysRepository;
        this.sheduled = sheduled;
        this.holidaysClient = holidaysClient;
        this.dateChecker = dateChecker;
    }
//    @Scheduled(fixedRate = 1000000000)//cron = "0 0 22 ? * MON-FRI"
    public void run(){
        LocalDate now = LocalDate.now();
        if(now.getDayOfWeek().getValue()>= 1 && now.getDayOfWeek().getValue()<=5 && !isHoliday(now) && !dateChecker.ifInDatabase()){
        sheduled.SheduledUpdate();
        }
    }
    private boolean isHoliday(LocalDate date){
        HolidaysReader holidaysReader = new HolidaysReader(holidaysRepository);
        HolidaysUpdater inMemoryHolidaysUpdater = new HolidaysUpdater(holidaysClient,holidaysRepository);
        inMemoryHolidaysUpdater.update();
        return holidaysReader.findHolidaysByYear().stream()
                .anyMatch(holidaysResponse -> {
                    LocalDate holidayDate = LocalDate.parse(holidaysResponse.date(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return holidayDate.getMonth() == date.getMonth() && holidayDate.getDayOfMonth() == date.getDayOfMonth();
                });
    }
}
