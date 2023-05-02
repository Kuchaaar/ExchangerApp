package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.Holidays.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CurrencySheduler {
private final InMemoryHolidaysRepository inMemoryHolidaysRepository;
private final Sheduled sheduled;
private final HolidaysClient holidaysClient;
private final DateChecker dateChecker;

    public CurrencySheduler(InMemoryHolidaysRepository inMemoryHolidaysRepository,
                            Sheduled sheduled,
                            HolidaysClient holidaysClient,
                            DateChecker dateChecker) {
        this.inMemoryHolidaysRepository = inMemoryHolidaysRepository;
        this.sheduled = sheduled;
        this.holidaysClient = holidaysClient;
        this.dateChecker = dateChecker;
    }
    @Scheduled(fixedRate = 10000)//cron = "0 0 22 ? * MON-FRI"
    public void run(){
        LocalDate now = LocalDate.now();
        if(now.getDayOfWeek().getValue()>= 1 && now.getDayOfWeek().getValue()<=5 && !isHoliday(now) && !dateChecker.ifInDatabase()){
        sheduled.SheduledUpdate();
        }
    }
    private boolean isHoliday(LocalDate date){
        HolidaysReader holidaysReader = new HolidaysReader(inMemoryHolidaysRepository);
        HolidaysUpdater inMemoryHolidaysUpdater = new HolidaysUpdater(holidaysClient,inMemoryHolidaysRepository);
        inMemoryHolidaysUpdater.update();
        return holidaysReader.findHolidays().stream()
                .anyMatch(holidaysResponse -> {
                    LocalDate holidayDate = LocalDate.parse(holidaysResponse.date(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return holidayDate.getMonth() == date.getMonth() && holidayDate.getDayOfMonth() == date.getDayOfMonth();
                });
    }
}
