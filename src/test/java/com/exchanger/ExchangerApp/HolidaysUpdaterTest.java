package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.holidays.HolidaysUpdater;
import com.exchanger.currency.integration.holidays.HolidaysClient;
import com.exchanger.currency.integration.holidays.HolidaysResponse;
import com.exchanger.currency.peristence.holidays.InMemoryHolidaysRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HolidaysUpdaterTest {
    private static final String NAME_1 = "New Year";
    private static final String NAME_2 = "Christmas";
    private static final String NAME_3 = "St. Stephen's Day";
    private static final String DATE_1 = "2023-01-01";
    private static final String DATE_2 = "2023-12-25";
    private static final String DATE_3 = "2023-12-26";
    @Mock
    private HolidaysClient holidaysClient;
    private InMemoryHolidaysRepository holidaysRepository;
    private HolidaysUpdater holidaysUpdater;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        holidaysRepository = new InMemoryHolidaysRepository();
        holidaysUpdater = new HolidaysUpdater(holidaysClient, holidaysRepository);
    }

    @Test
    void updateTest(){
//      given
        int year = 111;
        when(holidaysClient.getHolidays(year)).thenReturn(mockHolidaysResponse());
//      when
        holidaysUpdater.update(year);
//      then
        assertEquals(sortedByHolidaysName(holidaysRepository.findHolidaysByYear()), sortedByHolidaysName(List.of(
                aHolidaysResponse(DATE_1, NAME_1),
                aHolidaysResponse(DATE_2, NAME_2),
                aHolidaysResponse(DATE_3, NAME_3)
        )));
    }

    private List<HolidaysResponse> sortedByHolidaysName(List<HolidaysResponse> holidaysResponse){
        return holidaysResponse.stream()
                .sorted(Comparator.comparing(HolidaysResponse::name))
                .toList();
    }

    private List<HolidaysResponse> mockHolidaysResponse(){
        return List.of(aHolidaysResponse(DATE_1, NAME_1),
                aHolidaysResponse(DATE_2, NAME_2),
                aHolidaysResponse(DATE_3, NAME_3));
    }

    private HolidaysResponse aHolidaysResponse(String date, String name){
        return new HolidaysResponse(date, name);
    }
}