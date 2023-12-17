package com.exchanger.currency.peristence.holidays;

import com.exchanger.currency.domain.holidays.HolidaysRepository;
import com.exchanger.currency.integration.holidays.HolidaysResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(
        value = "repository.holidays",
        havingValue = "memory"
)
public class InMemoryHolidaysRepository implements HolidaysRepository {
    private final Map<String, HolidaysResponse> holidaysResponseMap = new HashMap<>();

    @Override
    public void saveHolidays(List<HolidaysResponse> list){
        list.forEach(holidaysResponse -> holidaysResponseMap.put(holidaysResponse.name(),
                holidaysResponse));
    }

    @Override
    public List<HolidaysResponse> findAllHolidays(){
        return holidaysResponseMap.values()
                .stream()
                .toList();
    }

    @Override
    public void deleteAllHolidays(){
        holidaysResponseMap.clear();
    }
}