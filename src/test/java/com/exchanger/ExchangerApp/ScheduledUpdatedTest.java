package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.currency.CurrencyUpdater;
import com.exchanger.currency.domain.holidays.HolidaysUpdater;
import com.exchanger.currency.integration.currency.CurrencyClient;
import com.exchanger.currency.services.sheduling.DatabaseChecker;
import com.exchanger.currency.services.sheduling.ScheduledUpdated;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

class ScheduledUpdatedTest {

    @Mock
    private HolidaysUpdater holidaysUpdater;

    @Mock
    private CurrencyUpdater currencyUpdater;

    @Mock
    private CurrencyClient currencyClient;

    @Mock
    private DatabaseChecker databaseChecker;

    @InjectMocks
    private ScheduledUpdated scheduledUpdated;

    public ScheduledUpdatedTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCurrencyUpdateWhenDataNotInDatabase() {
        // given
        when(databaseChecker.ifDataInDatabase(any())).thenReturn(false);
        when(currencyClient.getByTable("a")).thenReturn(emptyList());
        when(currencyClient.getByTable("b")).thenReturn(emptyList());

        // when
        scheduledUpdated.currencyUpdate();

        // then
        verify(currencyUpdater).update("a");
        verify(currencyUpdater).update("b");
    }

    @Test
    void testCurrencyUpdateWhenDataInDatabase() {
        // given
        when(databaseChecker.ifDataInDatabase(any())).thenReturn(true);

        // when
        scheduledUpdated.currencyUpdate();

        // then
        verify(currencyUpdater, never()).update("a");
        verify(currencyUpdater, never()).update("b");
    }

    @Test
    void testHolidaysUpdate() {
        // given
        int currentYear = LocalDate.now().getYear();

        // when
        scheduledUpdated.holidaysUpdate();

        // then
        verify(holidaysUpdater).deleteAll();
        verify(holidaysUpdater).update(currentYear);
    }
}