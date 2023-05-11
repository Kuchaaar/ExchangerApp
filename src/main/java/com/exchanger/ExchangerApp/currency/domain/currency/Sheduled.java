package com.exchanger.ExchangerApp.currency.domain.currency;
import com.exchanger.ExchangerApp.currency.domain.holidays.HolidaysUpdater;
import org.springframework.stereotype.Component;

@Component
public class Sheduled {

    private final HolidaysUpdater holidaysUpdater;
    private final CurrencyUpdater currencyUpdater;

    public Sheduled(HolidaysUpdater holidaysUpdater, CurrencyUpdater currencyUpdater) {
        this.currencyUpdater = currencyUpdater;
        this.holidaysUpdater = holidaysUpdater;
    }
    public void SheduledUpdate(){
        currencyUpdater.update("a");
        currencyUpdater.update("b");
    }
    public void HolidaysUpdate(){
        holidaysUpdater.update();
    }

}
