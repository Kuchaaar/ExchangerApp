package com.exchanger.currency.domain.holidays;
import com.exchanger.currency.integration.holidays.HolidaysResponse;

import java.util.List;

public interface HolidaysRepository {
    void saveHolidays(List<HolidaysResponse> holidaysResponseList);

    List<HolidaysResponse> findHolidaysByYear();
    void deleteAllHolidays();
}
