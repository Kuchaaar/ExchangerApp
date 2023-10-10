package com.exchanger.currency.domain.holidays;

import com.exchanger.currency.integration.holidays.HolidaysClient;
import com.exchanger.currency.integration.holidays.HolidaysResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component public class HolidaysUpdater {
    private final HolidaysClient holidaysClient;
    private final HolidaysRepository holidaysRepository;

    public HolidaysUpdater(HolidaysClient holidaysClient, HolidaysRepository holidaysRepository){
        this.holidaysClient = holidaysClient;
        this.holidaysRepository = holidaysRepository;
    }

    public void deleteAll(){
        holidaysRepository.deleteAllHolidays();
    }

    public void update(int year){
        List<HolidaysResponse> holidaysResponses = fetchHolidaysResponse(year);
        holidaysRepository.saveHolidays(holidaysResponses);
    }

    private List<HolidaysResponse> fetchHolidaysResponse(int year){
        return holidaysClient.getHolidays(year);
    }
}