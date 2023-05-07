package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyClient;
import com.exchanger.ExchangerApp.currency.peristence.currency.DatabaseCurrencyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sheduled {
    private final CurrencyClient currencyClient;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DatabaseCurrencyRepository databaseCurrencyRepository(NamedParameterJdbcTemplate jdbcTemplate){
        return new DatabaseCurrencyRepository(jdbcTemplate);
    }

    public Sheduled(CurrencyClient currencyClient, NamedParameterJdbcTemplate jdbcTemplate1) {
        this.currencyClient = currencyClient;
        this.jdbcTemplate = jdbcTemplate1;
    }
    public void SheduledUpdate(){
        CurrencyUpdater databaseUpdater = new CurrencyUpdater(currencyClient,databaseCurrencyRepository(jdbcTemplate));
        databaseUpdater.update("a");
        databaseUpdater.update("b");
    }

}
