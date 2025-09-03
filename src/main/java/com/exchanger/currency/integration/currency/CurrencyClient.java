package com.exchanger.currency.integration.currency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "currency")
public interface CurrencyClient {

    @GetMapping(value = "{table}")
    List<CurrenciesResponse> getByTable(@PathVariable("table") String table);

    @GetMapping(value = "{table}/last/{topCount}/")
    List<CurrenciesResponse> getByTable(@PathVariable("table") String table, @PathVariable("topCount") int topCount);

    @GetMapping(value = "{table}/{startDate}/{endDate}/")
    List<CurrenciesResponse> getByDates(@PathVariable("table") String table,
                                        @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);
}