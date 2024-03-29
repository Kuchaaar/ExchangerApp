package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepositoryJPA extends JpaRepository<Currency, Long> {
    List<Currency> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT(c.date) FROM Currency c")
    Page<LocalDate> findDistinctByDate(Pageable pageable);

    @Query("SELECT DISTINCT(c.code) FROM Currency c")
    Page<String> findDistinctByCode(Pageable pageable);

    @Query("SELECT DISTINCT(c.date) FROM Currency c WHERE c.code = :code")
    Page<LocalDate> findDistinctByDateByCode(@Param("code") String code,Pageable pageable);

    @Query("SELECT NEW com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate(c1, c2) " +
            "FROM Currency c1 " +
            "LEFT JOIN Currency c2 ON c1.code = c2.code AND c2.date = :endDate " +
            "WHERE c1.date = :startDate " +
            "UNION " +
            "SELECT NEW com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate(c1, c2) " +
            "FROM Currency c1 " +
            "RIGHT JOIN Currency c2 ON c1.code = c2.code AND c1.date = :startDate " +
            "WHERE c2.date = :endDate")
    List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    @Query("SELECT (COUNT(*) > 0) FROM Currency WHERE date=:date")
    boolean isDateInData(LocalDate date);

    List<Currency> findAllByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);
}