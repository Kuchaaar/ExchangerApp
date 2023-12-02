package com.exchanger.currency.integration.holidays;

import com.exchanger.currency.config.RetryerConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "holidays", configuration = RetryerConfig.class)
public interface HolidaysClient {
    @GetMapping(value = "{Year}/PL")
    List<HolidaysResponse> getHolidays(@PathVariable("Year") int year);
}