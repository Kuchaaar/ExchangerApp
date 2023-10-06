package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.currency.DatabaseChecker;
import com.exchanger.currency.domain.currency.ScheduledUpdated;
import com.exchanger.currency.domain.currency.Scheduler;
import com.exchanger.currency.domain.holidays.HolidaysRepository;
import com.exchanger.currency.integration.holidays.HolidaysResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class SchedulerTest {

    private Scheduler scheduler;

    @MockBean
    private DatabaseChecker databaseChecker;

    @MockBean
    private HolidaysRepository holidaysRepository;

    @MockBean
    private ScheduledUpdated scheduledUpdated;

    public SchedulerTest(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Test
    void testCurrencyRun() {
        LocalDate mondayDate = LocalDate.of(2023, 1, 2); // Poniedziałek
        LocalDate holidayDate = LocalDate.of(2023, 1, 1); // Święto
        LocalDate weekendDate = LocalDate.of(2023, 1, 7); // Sobota

        Mockito.when(databaseChecker.ifDateInDatabase(mondayDate)).thenReturn(false);
        Mockito.when(holidaysRepository.findHolidaysByYear()).thenReturn(new ArrayList<>());
        scheduler.currencyRun();

        Mockito.when(databaseChecker.ifDateInDatabase(holidayDate)).thenReturn(false);
        Mockito.when(holidaysRepository.findHolidaysByYear()).thenReturn(createHolidayList(holidayDate));
        scheduler.currencyRun();
        Mockito.when(databaseChecker.ifDateInDatabase(weekendDate)).thenReturn(false);
        Mockito.when(holidaysRepository.findHolidaysByYear()).thenReturn(new ArrayList<>());
        scheduler.currencyRun();
        Mockito.verify(scheduledUpdated, Mockito.times(1)).currencyUpdate();
    }

    @Test
    void testHolidaysRun() {
        scheduler.holidaysRun();
        Mockito.verify(scheduledUpdated, Mockito.times(1)).holidaysUpdate();
    }

    private List<HolidaysResponse> createHolidayList(LocalDate date) {
        List<HolidaysResponse> holidayList = new ArrayList<>();
        HolidaysResponse holidayResponse = new HolidaysResponse(date.toString(),"test");
        holidayList.add(holidayResponse);
        return holidayList;
    }
}
