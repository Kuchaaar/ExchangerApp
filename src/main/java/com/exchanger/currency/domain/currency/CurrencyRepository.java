package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
public interface CurrencyRepository {
    void saveAll(List<CurrencyResponse> list);
    Page<String> availableCodes(Pageable pageable);

    List<Currency> findByDates(LocalDate date1, LocalDate date2);

    boolean isDateInData(LocalDate date);

    Page<LocalDate> availableDates(Pageable pageable);

    List<Currency> findAll();
    Page<LocalDate> availableDatesForCurrency(String code,Pageable pageable);

    List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code);
    List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
    void deleteAll();
}