package com.exchanger.currency.persistence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "memory"
)
public class InMemoryCurrencyRepository implements CurrencyRepository{
    private final List<Currency> currencies = new ArrayList<>();


    @Override
    public void saveAll(List<CurrencyResponse> list){
        list.forEach(currencyResponse -> currencies.add(Currency.from(currencyResponse)));
    }

    @Override
    public Page<String> availableCodes(Pageable pageable){
        long offset = pageable.getOffset();
        long max = pageable.getOffset() + pageable.getPageSize();
        int total = currencies.stream().map(Currency::getCode).distinct().toList().size();
        List<String> result;
        if(currencies.size() < max){
            result = currencies.stream().map(Currency::getCode).distinct().toList().subList(Math.toIntExact(
                    offset), currencies.size());
        }
        else{
            result = currencies.stream().map(Currency::getCode).distinct().toList().subList(Math.toIntExact(
                    offset), Math.toIntExact(max));
        }
        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return currencies.stream()
                .filter(currency -> currency.getDate().isAfter(date1) && currency.getDate().isBefore(date2))
                .toList();
    }

    @Override
    public boolean isDateInData(LocalDate date){
        return currencies.stream()
                .map(currency -> currency.getDate().isEqual(date))
                .findFirst()
                .orElse(false);
    }

    @Override
    public Page<LocalDate> availableDates(Pageable pageable){
        long offset = pageable.getOffset();
        long max = pageable.getOffset() + pageable.getPageSize();
        int total = currencies.stream().map(Currency::getDate).distinct().toList().size();
        List<LocalDate> result;
        if(currencies.size() < max){
            result = currencies.stream().map(Currency::getDate).distinct().toList().subList(Math.toIntExact(
                    offset), currencies.size());
        }
        else{
            result = currencies.stream().map(Currency::getDate).distinct().toList().subList(Math.toIntExact(
                    offset), Math.toIntExact(max));
        }
        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Currency> findAll(){
        return new ArrayList<>(currencies);
    }

    @Override
    public Page<LocalDate> availableDatesForCurrency(String code, Pageable pageable){
        int total = currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .map(Currency::getDate)
                .distinct()
                .toList().size();

        long offset = pageable.getOffset();
        long max = pageable.getOffset() + pageable.getPageSize();
        List<LocalDate> result;
        if(currencies.size() < max){
            result = currencies.stream()
                    .filter(currency -> currency.getCode().equals(code))
                    .map(Currency::getDate)
                    .distinct()
                    .toList().subList(Math.toIntExact(offset), currencies.size());
        }
        else{
            result = currencies.stream()
                    .filter(currency -> currency.getCode().equals(code))
                    .map(Currency::getDate)
                    .distinct()
                    .toList().subList(Math.toIntExact(offset), Math.toIntExact(max));
        }
        return new PageImpl<>(result, pageable, total);
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

        for(int i = 0; i < currencies.size(); i++){
            Currency currencyStartDate = currencies.get(i);

            if(currencyStartDate.getDate().isEqual(startDate)){
                for(int j = i + 1; j < currencies.size(); j++){
                    Currency currencyEndDate = currencies.get(j);

                    if(currencyEndDate.getDate().isEqual(endDate) &&
                            currencyStartDate.getCode().equals(currencyEndDate.getCode())){
                        resultList.add(new CurrencyFromStartDateAndEndDate(currencyStartDate, currencyEndDate));
                    }
                }
            }
        }

        return resultList;
    }

    @Override
    public void deleteAll(){
        currencies.clear();
    }
}