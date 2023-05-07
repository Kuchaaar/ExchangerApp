package com.exchanger.ExchangerApp.currency.integration.currency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@FeignClient(name = "currency")
public interface CurrencyClient {

    @RequestMapping(method = RequestMethod.GET, value = "{table}")
    List<CurrenciesResponse> getByTable(@PathVariable("table") String table);


    @RequestMapping(method = RequestMethod.GET, value = "{table}/last/{topCount}/")
    List<CurrenciesResponse> getByTable(@PathVariable("table") String table, @PathVariable("topCount") int topCount);
}
