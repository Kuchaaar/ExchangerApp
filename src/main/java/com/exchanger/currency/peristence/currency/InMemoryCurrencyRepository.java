package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(
        value = "repository.mock.currency.mock.enable",
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
