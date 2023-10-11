package com.exchanger;

import com.exchanger.currency.services.sheduling.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "fetchDataOnStartup",
        havingValue = "true"
)
public class OnStartUpDataFetcher implements CommandLineRunner {
    //declare

    private final Scheduler scheduler;

    public OnStartUpDataFetcher(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run(String... args) throws Exception {
        scheduler.currencyRun();
        scheduler.holidaysRun();
    }
}