package com.exchanger.ExchangerApp.currency.domain;


import com.exchanger.ExchangerApp.currency.integration.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import com.exchanger.ExchangerApp.currency.peristence.InMemoryCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Component
public class CurrencyUpdater {
    private final CurrencyClient currencyClient;
    private final CurrencyRepository currencyRepository;


    public CurrencyUpdater(CurrencyClient currencyClient, @Qualifier("DatabaseCurrencyRepository") CurrencyRepository currencyRepository) {
        this.currencyClient = currencyClient;
        this.currencyRepository = currencyRepository;
    }

    public void update() {
        List<CurrencyResponse> currencyResponses = fetchCurrenciesResponse();
        currencyRepository.saveAll(currencyResponses);
    }

    private List<CurrencyResponse> fetchCurrenciesResponse() {
        return currencyClient.getByTable("a").stream()
                .flatMap(currenciesResponse -> {
                    List<CurrencyResponse> ratesWithEffectiveDate = new ArrayList<>();
                    String effectiveDate = currenciesResponse.effectiveDate();
                    for (CurrencyResponse rate : currenciesResponse.rates()) {
                        ratesWithEffectiveDate.add(new CurrencyResponse(rate.currency(),
                                rate.code(),
                                rate.mid(),
                                effectiveDate));
                    }
                    return ratesWithEffectiveDate.stream();
                })
                .toList();
    }
}


