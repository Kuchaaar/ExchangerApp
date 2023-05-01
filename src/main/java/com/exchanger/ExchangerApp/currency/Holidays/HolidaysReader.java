package com.exchanger.ExchangerApp.currency.Holidays;

import com.exchanger.ExchangerApp.currency.domain.Currency;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class HolidaysReader {
    private final HolidaysRepository holidaysRepository;

    public HolidaysReader(@Qualifier("InMemoryHolidaysRepository") HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }
    public List<HolidaysResponse> findHolidays() {
        return holidaysRepository.findHolidays();
    }
}
