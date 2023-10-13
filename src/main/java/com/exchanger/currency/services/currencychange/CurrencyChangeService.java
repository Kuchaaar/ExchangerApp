package com.exchanger.currency.services.currencychange;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CurrencyChangeService {
    public final CurrencyRepository currencyRepository;

    public CurrencyChangeService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChange(
            FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        List<Currency> currencies = findCurrenciesByDate(findCurrencyWithHighestRatePercentageChangeRequest);
        List<CurrencyCodeWithPercentage> currencyList = currencyWithPercentagesList(currencies);
        currencyList.sort((c1, c2) -> Double.compare(c2.percentageChange().doubleValue(),
                c1.percentageChange().doubleValue()));
        List<CurrencyCodeWithPercentage> list =
                currencyList.subList(0, findCurrencyWithHighestRatePercentageChangeRequest.number());
        return new FindCurrencyWithHighestRatePercentageChangeResponse(list, List.of());
    }

    private List<Currency> findCurrenciesByDate(FindCurrencyWithHighestRatePercentageChangeRequest findCurrencyWithHighestRatePercentageChangeRequest){
        List<Currency> currencies =
                currencyRepository.findByDate(findCurrencyWithHighestRatePercentageChangeRequest.startDate());
        List<Currency> currencies1 =
                currencyRepository.findByDate(findCurrencyWithHighestRatePercentageChangeRequest.endDate());
        /*
        List<Obiekt(CurrencyFromStartDate,CurrencyFromEndDate)>

         */
        return Stream.concat(currencies.stream(), currencies1.stream()).toList();
    }

    private List<CurrencyCodeWithPercentage> currencyWithPercentagesList(List<Currency> currencies){
        Map<String, Currency> currencyMap = new HashMap<>();
        List<CurrencyCodeWithPercentage> result = new ArrayList<>();
        for(Currency currency : currencies){
            String code = currency.getCode();
            if(currencyMap.containsKey(code)){
                Currency existingCurrency = currencyMap.get(code);
                BigDecimal difference = (currency.getMid()
                        .multiply(BigDecimal.valueOf(100.0))
                        .divide(existingCurrency.getMid(),RoundingMode.UP)
                        .subtract((BigDecimal.valueOf(100.0))))
                        .setScale(2,RoundingMode.UP);
                result.add(new CurrencyCodeWithPercentage(code, difference));
            }else{
                currencyMap.put(code, currency);
            }
        }
        return result;
    }
}