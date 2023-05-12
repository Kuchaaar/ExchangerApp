package com.exchanger.currency.domain.currency;
import org.springframework.stereotype.Component;
import java.time.LocalDate;


@Component
public class MainClass {
    private final CurrencyRepository currencyRepository;

    public MainClass(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void ExtractData(LocalDate start, LocalDate stop) {
        if(start == stop){
            currencyRepository.findByDate(start.toString());
        }else{
            currencyRepository.findByDates(start.toString(),stop.toString());
        }
    }
}
