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
    private final ScheduledUpdated scheduledUpdated;

    public Scheduler(
            DatabaseChecker databaseChecker, HolidaysRepository holidaysRepository, ScheduledUpdated scheduledUpdated){
        this.databaseChecker = databaseChecker;
        this.holidaysRepository = holidaysRepository;
        this.scheduledUpdated = scheduledUpdated;
    }

    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    public void currencyRun(){
        LocalDate now = LocalDate.now();
        if(! isHoliday(now) && !databaseChecker.ifDateInDatabase(now)){
            scheduledUpdated.currencyUpdate();

        }
    }

    @Scheduled(cron = "0 0 2 1 1 *")
    public void holidaysRun(){
        scheduledUpdated.holidaysUpdate();
    }

    public void fetchFirstCurrencies(){
        LocalDate today = LocalDate.now();
        LocalDate twoYearsAgo = today.minusYears(2);

        LocalDate start = twoYearsAgo;
        LocalDate end = start.plusDays(90);

        while (start.isBefore(today)) {
            if (end.isAfter(today)) {
                end = today.minusDays(1);
            }
            scheduledUpdated.currencyUpdate(start,end);
            start = end.plusDays(1);
            end = start.plusDays(90);
        }
    }

    private boolean isHoliday(LocalDate date){
        return holidaysRepository.findAllHolidays().stream()
                .anyMatch(holidaysResponse -> {
                    LocalDate holidayDate = LocalDate.parse(holidaysResponse.date(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return holidayDate.getMonth() == date.getMonth()
                            && holidayDate.getDayOfMonth() == date.getDayOfMonth();
                });
    }


}