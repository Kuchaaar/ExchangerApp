package com.exchanger.ExchangerApp.currency.Holidays;

import com.exchanger.ExchangerApp.currency.integration.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collection;
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
