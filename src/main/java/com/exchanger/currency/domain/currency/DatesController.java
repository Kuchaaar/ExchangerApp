package com.exchanger.currency.domain.currency;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DatesController {
    private final CurrencyRepository currencyRepository;

    public DatesController(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    @PostMapping("/available_dates")
    public List<String> availableDates(){
        return currencyRepository.availableDates();
    }
}
