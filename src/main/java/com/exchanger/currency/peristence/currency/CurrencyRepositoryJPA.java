package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepositoryJPA extends JpaRepository<Currency, Long> {
    List<Currency> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT(c.date) FROM Currency c")
    List<String> findDistinctByDate();

    List<Currency> findAllByDate(LocalDate date);

    List<Currency> findAllByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);
}
