package com.exchanger.currency.domain.currency;

import com.exchanger.currency.domain.holidays.HolidaysReader;
import com.exchanger.currency.domain.holidays.HolidaysUpdater;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CurrencySheduler {
    private final DateChecker dateChecker;
    private final HolidaysReader holidaysReader;
    private final Sheduled sheduled;

    public CurrencySheduler(HolidaysReader holidaysReader,
                            DateChecker dateChecker,
                            Sheduled sheduled) {
        this.dateChecker = dateChecker;
        this.holidaysReader = holidaysReader;
        this.sheduled = sheduled;
    }

    @Scheduled(fixedRate = 1000000000)//cron = "0 0 22 ? * MON-FRI"
    public void run() {
        LocalDate now = LocalDate.now();
        if (now.getDayOfWeek().getValue() >= 1 && now.getDayOfWeek().getValue() <= 5
                && !isHoliday(now) ) {//&& dateChecker.ifInDatabase(now)
//                sheduled.SheduledUpdate();
        }
        else{
//            sheduled.SheduledUpdate();
        }
    }

    private boolean isHoliday(LocalDate date) {
        return holidaysReader.findHolidaysByYear().stream()
                .anyMatch(holidaysResponse -> {
                    LocalDate holidayDate = LocalDate.parse(holidaysResponse.date(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return holidayDate.getMonth() == date.getMonth()
                            && holidayDate.getDayOfMonth() == date.getDayOfMonth();
                });
    }
}
