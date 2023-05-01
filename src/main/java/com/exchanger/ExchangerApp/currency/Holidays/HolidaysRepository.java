package com.exchanger.ExchangerApp.currency.Holidays;
import java.util.List;

public interface HolidaysRepository {
    void saveHolidays(List<HolidaysResponse> holidaysResponseList);

    List<HolidaysResponse> findHolidays();
}
