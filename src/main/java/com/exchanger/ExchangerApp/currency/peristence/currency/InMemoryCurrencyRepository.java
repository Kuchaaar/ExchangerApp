package com.exchanger.ExchangerApp.currency.peristence.currency;

import com.exchanger.ExchangerApp.currency.domain.currency.Currency;
import com.exchanger.ExchangerApp.currency.domain.currency.CurrencyRepository;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(
        value = "repository.mock.holidays.mock.enable",
        havingValue = "true"
)
public class InMemoryCurrencyRepository implements CurrencyRepository {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    

    @Override
    public void saveAll(List<CurrencyResponse> list) {
        list.forEach(currencyResponse -> currencyMap.put(currencyResponse.code(),
                Currency.from(currencyResponse)));
    }

    @Override
    public List<Currency> findAll() {
        return currencyMap.values()
                .stream()
                .toList();
    }
}
