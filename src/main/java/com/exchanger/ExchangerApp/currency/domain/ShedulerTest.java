package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.Holidays.HolidaysRepository;
import com.exchanger.ExchangerApp.currency.Holidays.HolidaysResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ShedulerTest {
private final HolidaysRepository holidaysRepository;

    public ShedulerTest(@Qualifier("InMemoryHolidaysRepository") HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }
    @Scheduled(fixedRate = 10)//cron = "0 0 22 ? * MON-FRI"
    public void run(){
        LocalDate now = LocalDate.now();
        if(now.getDayOfWeek().getValue()>= 1 && now.getDayOfWeek().getValue()<=5 && isHoliday(now)){
            System.out.println(isHoliday(now));
        }
        else{
            System.out.println("aha" + isHoliday(now));
        }
    }
    private boolean isHoliday(LocalDate date){
        List<HolidaysResponse> holidaysResponseList = holidaysRepository.findHolidays();
        return holidaysResponseList.stream().anyMatch(holidaysResponse -> holidaysResponse.date().equals(date));
    }
}
