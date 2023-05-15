package com.exchanger.currency.domain.currency;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final MainClass mainClass;
    public CurrencyController(MainClass mainClass) {
        this.mainClass = mainClass;
    }
    @PostMapping("/extract-data")
    public void extractData(@RequestParam("startDate") LocalDate startDate,
                                              @RequestParam("endDate") LocalDate endDate) {
        mainClass.ExtractData(startDate, endDate);
    }
}