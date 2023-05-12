package com.exchanger.currency.domain.currency;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> extractData(@RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        mainClass.ExtractData(start, end);
        return ResponseEntity.ok("Data extraction initiated successfully.");
    }
}