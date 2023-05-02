package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.Holidays.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ShedulerTest {
private final InMemoryHolidaysRepository inMemoryHolidaysRepository;
private final Sheduled sheduled;
private final HolidaysClient holidaysClient;

    public ShedulerTest(InMemoryHolidaysRepository inMemoryHolidaysRepository, Sheduled sheduled,HolidaysClient holidaysClient) {
        this.inMemoryHolidaysRepository = inMemoryHolidaysRepository;
        this.sheduled = sheduled;
        this.holidaysClient = holidaysClient;
    }
    @Scheduled(fixedRate = 1000)//cron = "0 0 22 ? * MON-FRI"
    public void run(){
        LocalDate now = LocalDate.now();
        if(now.getDayOfWeek().getValue()>= 1 && now.getDayOfWeek().getValue()<=5 && !isHoliday(now)){
        sheduled.SheduledUpdate();
        }
        else{
            System.out.println("XD");
        }
    }
    private boolean isHoliday(LocalDate date){
        HolidaysReader holidaysReader = new HolidaysReader(inMemoryHolidaysRepository);
        HolidaysUpdater inMemoryHolidaysUpdater = new HolidaysUpdater(holidaysClient,inMemoryHolidaysRepository);
        inMemoryHolidaysUpdater.update();
        return holidaysReader.findHolidays().stream().
                anyMatch(holidaysResponse -> holidaysResponse.date().
                        equals(date));
    }
}
