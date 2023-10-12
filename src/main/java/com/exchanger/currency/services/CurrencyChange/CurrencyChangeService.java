package com.exchanger.currency.services.CurrencyChange;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CurrencyChangeService {
    public final CurrencyRepository currencyRepository;

    public CurrencyChangeService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findCurrenciesByDate(CurrencyCalculatorRequest currencyCalculatorRequest){
        List<Currency> currencies = currencyRepository.findByDate(currencyCalculatorRequest.startDate());
        List<Currency> currencies1 = currencyRepository.findByDate(currencyCalculatorRequest.endDate());
        return Stream.concat(currencies.stream(), currencies1.stream()).toList();
    }

    public List<CurrencyWithPercentages> findCurrenciesValues(CurrencyCalculatorRequest currencyCalculatorRequest){
        List<Currency> currencies = findCurrenciesByDate(currencyCalculatorRequest);
        List<CurrencyWithPercentages> currencyList = currencyWithPercentagesList(currencies);
        currencyList.sort((c1, c2) -> Integer.compare(c2.percentages(), c1.percentages()));
        return currencyList.subList(0,currencyCalculatorRequest.number());
    }

    public List<CurrencyWithPercentages> currencyWithPercentagesList(
            List<Currency> currencies)
    {
        Map<String, Currency> currencyMap = new HashMap<>();
        List<CurrencyWithPercentages> result = new ArrayList<>();
        for (Currency currency : currencies) {
            String code = currency.getCode();
            if (currencyMap.containsKey(code)) {
                Currency existingCurrency = currencyMap.get(code);
                double difference = (currency.getMid()/existingCurrency.getMid() * 100) - 100;
                result.add(new CurrencyWithPercentages(code, (int) difference));
            } else {
                currencyMap.put(code, currency);
            }
        }
        return result;
    }
}
