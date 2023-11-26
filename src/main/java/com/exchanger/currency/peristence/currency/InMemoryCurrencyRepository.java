package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "memory"
)
public class InMemoryCurrencyRepository implements CurrencyRepository {
    private final List<Currency> currencies = new ArrayList<>();


    @Override
    public void saveAll(List<CurrencyResponse> list){
        list.forEach(currencyResponse -> currencies.add(Currency.from(currencyResponse)));
    }

    @Override
    public List<String> availableCodes(){
        return currencies.stream()
                .map(Currency::getCode)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return currencies.stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .toList();
    }

    @Override
    public boolean isDateInData(LocalDate date){
        for (Currency currency : currencies) {
            if (currency.getDate().isEqual(date)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<LocalDate> availableDates(){
        return currencies.stream()
                .map(Currency::getDate)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findAll(){
        return currencies
                .stream()
                .toList();
    }

    @Override
    public List<LocalDate> availableDatesForCurrency(String code){
        return currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .map(Currency::getDate)
                .distinct()
                .toList();
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return currencies.stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .filter(currency -> currency.getCode().equals(code))
                .toList();
    }

    @Override
    public List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate,
                                                                                     LocalDate endDate){
        List<CurrencyFromStartDateAndEndDate> resultList = new ArrayList<>();

        for (int i = 0; i < currencies.size(); i++) {
            Currency currencyStartDate = currencies.get(i);

            if (currencyStartDate.getDate().isEqual(startDate)) {
                for (int j = i + 1; j < currencies.size(); j++) {
                    Currency currencyEndDate = currencies.get(j);

                    if (currencyEndDate.getDate().isEqual(endDate) && currencyStartDate.getCode().equals(currencyEndDate.getCode())) {
                        resultList.add(new CurrencyFromStartDateAndEndDate(currencyStartDate, currencyEndDate));
                    }
                }
            }
        }

        return resultList;
    }

}