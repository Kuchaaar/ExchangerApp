package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepositoryJPA extends JpaRepository<Currency, Long> {
    List<Currency> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT(c.date) FROM Currency c")
    List<LocalDate> findDistinctByDate();
    @Query("SELECT DISTINCT(c.code) FROM Currency c")
    List<String> findDistinctByCode();
    @Query("SELECT DISTINCT(c.date) FROM Currency c WHERE c.code = :code")
    List<LocalDate> findDistinctByDateByCode(@Param("code")String code);
    @Query("SELECT NEW com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate(c1, c2) FROM Currency c1, Currency c2 WHERE c1.code = c2.code AND c1.date = :startDate AND c2.date = :endDate")
    List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    List<Currency> findAllByDate(LocalDate date);

    List<Currency> findAllByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);
}