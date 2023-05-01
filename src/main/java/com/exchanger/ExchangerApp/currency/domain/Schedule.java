package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Bean;

public class Schedule implements Job {
    private final CurrencyClient currencyClient;
    private final CurrencyUpdater currencyUpdater;
    private final DatabaseCurrencyRepository databaseCurrencyRepository;

    public Schedule(CurrencyClient currencyClient, CurrencyUpdater currencyUpdater, DatabaseCurrencyRepository databaseCurrencyRepository) {
        this.currencyClient = currencyClient;
        this.currencyUpdater = currencyUpdater;
        this.databaseCurrencyRepository = databaseCurrencyRepository;
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        currencyUpdater.update();
    }
}
