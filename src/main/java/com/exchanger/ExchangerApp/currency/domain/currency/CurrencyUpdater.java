package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.integration.currency.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Stream;

@Component
public class CurrencyUpdater {
    private final CurrencyClient currencyClient;
    private final CurrencyRepository currencyRepository;

    public CurrencyUpdater(CurrencyClient currencyClient, CurrencyRepository currencyRepository) {
        this.currencyClient = currencyClient;
        this.currencyRepository = currencyRepository;
    }

    public void update(String table, int topCount) {
        final List<CurrencyResponse> currencyResponses = fetchCurrenciesResponse(table, topCount);
        currencyRepository.saveAll(currencyResponses);
    }

    public void update(String table) {
        final List<CurrencyResponse> currencyResponses = fetchCurrenciesResponse(table);
        currencyRepository.saveAll(currencyResponses);
    }

    private List<CurrencyResponse> fetchCurrenciesResponse(String table, int topCount) {
        return mapToCurrenciesResponse(currencyClient.getByTable(table, topCount));
    }

    private List<CurrencyResponse> fetchCurrenciesResponse(String table) {
        return mapToCurrenciesResponse(currencyClient.getByTable(table));
    }

    private List<CurrencyResponse> mapToCurrenciesResponse(List<CurrenciesResponse> currenciesResponses) {
        return currenciesResponses.stream()
                .flatMap(this::currencyResponsesWithEffectiveDate)
                .toList();
    }

    private Stream<CurrencyResponse> currencyResponsesWithEffectiveDate(CurrenciesResponse currenciesResponse) {
        final String effectiveDate = currenciesResponse.effectiveDate();
        return currenciesResponse.rates().stream()
                .map(rate -> aCurrencyResponse(effectiveDate, rate));
    }

    private CurrencyResponse aCurrencyResponse(String effectiveDate, CurrencyResponse rate) {
        return new CurrencyResponse(
                rate.currency(),
                rate.code(),
                rate.mid(),
                effectiveDate);
    }
}


