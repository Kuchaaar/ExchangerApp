package com.exchanger.ExchangerApp.currency.peristence.holidays;

import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysRepository;
import com.exchanger.ExchangerApp.currency.integration.holidays.HolidaysResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(
        value = "repository.mock.holidays.mock.enable",
        havingValue = "true"
)
public class InMemoryHolidaysRepository implements HolidaysRepository {
    private final Map<String, HolidaysResponse> holidaysResponseMap = new HashMap<>();
    @Override
    public void saveHolidays(List<HolidaysResponse> list) {
        list.forEach(HolidaysResponse -> holidaysResponseMap.put(HolidaysResponse.name(),
                HolidaysResponse));
    }

    @Override
    public List<HolidaysResponse> findHolidaysByYear() {
        return holidaysResponseMap.values()
                .stream()
                .toList();
    }
}
