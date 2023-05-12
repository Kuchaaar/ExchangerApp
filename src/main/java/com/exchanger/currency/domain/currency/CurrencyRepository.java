package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;

import java.util.List;

public interface CurrencyRepository {
    void saveAll(List<CurrencyResponse> list);
    List<Currency> findByDates(String date1, String date2);
    List<Currency> findByDate(String date);
    List<String> availableDate();

    List<Currency> findAll();
}
