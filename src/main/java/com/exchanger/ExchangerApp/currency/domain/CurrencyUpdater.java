package com.exchanger.ExchangerApp.currency.domain;


import com.exchanger.ExchangerApp.currency.integration.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
@Component
public class CurrencyUpdater {

    private final CurrencyClient currencyClient;
    private final CurrencyRepository currencyRepository;

    public CurrencyUpdater(CurrencyClient currencyClient,@Qualifier("DatabaseCurrencyRepository") CurrencyRepository currencyRepository) {
        this.currencyClient = currencyClient;
        this.currencyRepository = currencyRepository;
    }

    public void update() {
        List<CurrencyResponse> currencyResponses = fetchCurrenciesResponse();
        currencyRepository.saveAll(currencyResponses);
    }

    private List<CurrencyResponse> fetchCurrenciesResponse() {
        return currencyClient.getByTable("a").stream()
                .map(CurrenciesResponse::rates)
                .flatMap(Collection::stream)
                .toList();
    }
}


