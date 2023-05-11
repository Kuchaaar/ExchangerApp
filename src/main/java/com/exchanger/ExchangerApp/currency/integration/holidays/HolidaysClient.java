package com.exchanger.ExchangerApp.currency.integration.holidays;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "holidays")
public interface HolidaysClient {
    @GetMapping(value = "{Year}/PL")
    List<HolidaysResponse> getHolidays(@PathVariable("Year") int Year);
}
