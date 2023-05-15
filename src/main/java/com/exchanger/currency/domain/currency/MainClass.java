package com.exchanger.currency.domain.currency;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;


@Component
public class MainClass {
    private final CurrencyRepository currencyRepository;

    public MainClass(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> ExtractData(LocalDate start, LocalDate stop) {
        if(start == stop){
            return currencyRepository.findByDate(start);
        }else{
            return currencyRepository.findByDates(start,stop);
        }
    }
}
