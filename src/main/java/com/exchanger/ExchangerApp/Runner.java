package com.exchanger.ExchangerApp;
import com.exchanger.ExchangerApp.currency.domain.CurrencyExcel;
import com.exchanger.ExchangerApp.currency.domain.CurrencyFetcher;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.CurrencyDatabase;
import com.exchanger.ExchangerApp.currency.integration.CurrencyRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationListener<ApplicationReadyEvent> {

    private final CurrencyFetcher currencyFetcher;
    private final CurrencyRepo currencyRepo;
    private final CurrencyClient currencyClient;
    private final CurrencyDatabase currencyDatabase;
    private final CurrencyExcel currencyExcel;

    public Runner(CurrencyFetcher currencyFetcher, CurrencyRepo currencyRepo, CurrencyClient currencyClient, CurrencyDatabase currencyDatabase, CurrencyExcel currencyExcel) {
        this.currencyFetcher = currencyFetcher;
        this.currencyRepo = currencyRepo;
        this.currencyClient = currencyClient;
        this.currencyDatabase = currencyDatabase;
        this.currencyExcel = currencyExcel;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        currencyFetcher.showAll();
        currencyRepo.saveAll(currencyClient.getByTable("a")); //nie działa jak jest wiecej niz 1 ;c
        currencyDatabase.saveAll(currencyClient.getByTable("a"));
        currencyExcel.writeToExcel();

    }
}
