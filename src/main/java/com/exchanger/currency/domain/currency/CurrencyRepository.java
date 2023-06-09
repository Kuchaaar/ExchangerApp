package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepository {
    void saveAll(List<CurrencyResponse> list);
    List<Currency> findByDates(LocalDate date1, LocalDate date2);
    List<Currency> findByDate(LocalDate date);
    List<String> availableDates();

    List<Currency> findAll();
}
