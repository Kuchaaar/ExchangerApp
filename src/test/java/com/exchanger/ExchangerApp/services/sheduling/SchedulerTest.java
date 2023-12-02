package com.exchanger.ExchangerApp.services.sheduling;

import com.exchanger.currency.integration.holidays.HolidaysResponse;
import com.exchanger.currency.peristence.holidays.InMemoryHolidaysRepository;
import com.exchanger.currency.services.sheduling.DatabaseChecker;
import com.exchanger.currency.services.sheduling.ScheduledUpdated;
import com.exchanger.currency.services.sheduling.Scheduler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

class SchedulerTest {

    @Mock
    private DatabaseChecker databaseChecker;

    @Mock
    private InMemoryHolidaysRepository holidaysRepository;

    @Mock
    private ScheduledUpdated scheduledUpdated;

    @InjectMocks
    private Scheduler scheduler;

    public SchedulerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCurrencyRunWhenNotHolidayAndNotInDatabase() {
        //given
        LocalDate now = LocalDate.now();
        when(holidaysRepository.findAllHolidays()).thenReturn(emptyList());
        when(databaseChecker.ifDateInDatabase(now)).thenReturn(false);
        //when
        scheduler.currencyRun();
        //then
        verify(scheduledUpdated).currencyUpdate();
    }

    @Test
    void testCurrencyRunWhenHoliday() {
        //given
        LocalDate now = LocalDate.now();
        when(holidaysRepository.findAllHolidays()).thenReturn(List.of(new HolidaysResponse(LocalDate.now().toString(), "Holiday")));
        when(databaseChecker.ifDateInDatabase(now)).thenReturn(false);
        //when
        scheduler.currencyRun();
        //then
        verify(scheduledUpdated, never()).currencyUpdate();
    }

    @Test
    void testCurrencyRunWhenInDatabase() {
        //given
        LocalDate now = LocalDate.now();
        when(holidaysRepository.findAllHolidays()).thenReturn(emptyList());
        when(databaseChecker.ifDateInDatabase(now)).thenReturn(true);
        //when
        scheduler.currencyRun();
        //then
        verify(scheduledUpdated, never()).currencyUpdate();
    }

    @Test
    void testHolidaysRun() {
        //when
        scheduler.holidaysRun();
        //then
        verify(scheduledUpdated).holidaysUpdate();
    }
}