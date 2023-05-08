package com.exchanger.ExchangerApp.currency.domain.holidays;
import com.exchanger.ExchangerApp.currency.integration.holidays.HolidaysResponse;

import java.util.List;

public interface HolidaysRepository {
    void saveHolidays(List<HolidaysResponse> holidaysResponseList);

    List<HolidaysResponse> findHolidaysByYear();
}
