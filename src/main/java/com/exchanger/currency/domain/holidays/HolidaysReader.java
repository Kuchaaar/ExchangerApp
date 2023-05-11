package com.exchanger.currency.domain.holidays;
import com.exchanger.currency.integration.holidays.HolidaysResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component

public class HolidaysReader {
    private final HolidaysRepository holidaysRepository;

    public HolidaysReader(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    public List<HolidaysResponse> findHolidaysByYear() {
        return holidaysRepository.findHolidaysByYear();
    }
}
