package com.exchanger.currency.services.sheduling;

import com.exchanger.currency.domain.holidays.HolidaysRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Scheduler {
    private final DatabaseChecker databaseChecker;
    private final HolidaysRepository holidaysRepository;
    private final ScheduledUpdated sheduled;

    public Scheduler(
            DatabaseChecker databaseChecker, HolidaysRepository holidaysRepository, ScheduledUpdated sheduled){
        this.databaseChecker = databaseChecker;
        this.holidaysRepository = holidaysRepository;
        this.sheduled = sheduled;
    }

    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    public void currencyRun(){
        LocalDate now = LocalDate.now();
        if(! isHoliday(now) && ! databaseChecker.ifDateInDatabase(now)){
            sheduled.currencyUpdate();
        }
    }

    @Scheduled(cron = "0 0 2 1 1 *")
    public void holidaysRun(){
        sheduled.holidaysUpdate();
    }

    private boolean isHoliday(LocalDate date){
        return holidaysRepository.findHolidaysByYear().stream()
                .anyMatch(holidaysResponse -> {
                    LocalDate holidayDate = LocalDate.parse(holidaysResponse.date(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return holidayDate.getMonth() == date.getMonth()
                            && holidayDate.getDayOfMonth() == date.getDayOfMonth();
                });
    }
}