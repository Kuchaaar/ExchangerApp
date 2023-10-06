package com.exchanger.currency.domain.currency;

import com.exchanger.currency.domain.holidays.HolidaysRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CurrencySheduler {
    private final DateChecker dateChecker;
    private final HolidaysRepository holidaysRepository;
    private final Sheduled sheduled;

    public CurrencySheduler(DateChecker dateChecker,
                            HolidaysRepository holidaysRepository, Sheduled sheduled) {
        this.dateChecker = dateChecker;
        this.holidaysRepository = holidaysRepository;
        this.sheduled = sheduled;
    }

    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    public void run() {
        LocalDate now = LocalDate.now();
        if (now.getDayOfWeek().getValue() >= 1 && now.getDayOfWeek().getValue() <= 5
                && !isHoliday(now) && !dateChecker.ifInDatabase(now)) {
                sheduled.sheduledUpdate();
        }
    }
    @Scheduled(cron = "0 2 1 1 * *")
    public void holidaysRun(){
        sheduled.holidaysUpdate();
    }
    private boolean isHoliday(LocalDate date) {
        return holidaysRepository.findHolidaysByYear().stream()
                .anyMatch(holidaysResponse -> {
                    LocalDate holidayDate = LocalDate.parse(holidaysResponse.date(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return holidayDate.getMonth() == date.getMonth()
                            && holidayDate.getDayOfMonth() == date.getDayOfMonth();
                });
    }
}
