package com.exchanger.ExchangerApp.currency.domain.holidays;

import com.exchanger.ExchangerApp.currency.integration.holidays.HolidaysClient;
import com.exchanger.ExchangerApp.currency.integration.holidays.HolidaysResponse;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class HolidaysUpdater {
    private final HolidaysClient holidaysClient;
    private final HolidaysRepository holidaysRepository;

    public HolidaysUpdater(HolidaysClient holidaysClient,
                           @Qualifier("DatabaseHolidaysRepository") HolidaysRepository holidaysRepository) {
        this.holidaysClient = holidaysClient;
        this.holidaysRepository = holidaysRepository;
    }
    public void update() {
        List<HolidaysResponse> holidaysResponses = fetchHolidaysResponse();
        holidaysRepository.saveHolidays(holidaysResponses);
    }

    private List<HolidaysResponse> fetchHolidaysResponse() {
        return holidaysClient.getHolidays(2023);
    }
}