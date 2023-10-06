package com.exchanger.currency.response;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exchanger.currency.domain.currency.Currency;

import java.util.List;

@RestController("/")
public class IndexController {
    private final CurrencyRepository currencyRepository;

    public IndexController(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @GetMapping("test")
    public List<Currency> getAll(){return currencyRepository.findAll();}

}
