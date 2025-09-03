package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
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
    List<LocalDate> findDistinctByDate();

    @Query("SELECT DISTINCT(c.code) FROM Currency c")
    List<String> findDistinctByCode();

    @Query("SELECT (COUNT(*) > 0) FROM Currency WHERE date=:date")
    boolean isDateInData(LocalDate date);

    @Query("SELECT DISTINCT(c.code) FROM Currency c")
    List<String> availableCodesWithoutPagination();
    List<Currency> findAllByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);
}