package com.exchanger.ExchangerApp.currency.integration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@FeignClient(name = "currency")
public interface CurrencyClient {

    @RequestMapping(method = RequestMethod.GET, value = "{table}")
    List<CurrenciesResponse> getByTable(@PathVariable("table") String table);

    @RequestMapping(method = RequestMethod.GET, value = "{table}/last/{topCount}")
    List<CurrenciesResponse> getByTable(@PathVariable("table") String table, @PathVariable("topCount") int topCount);
}
