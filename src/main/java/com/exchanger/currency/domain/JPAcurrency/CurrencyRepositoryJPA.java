package com.exchanger.currency.domain.JPAcurrency;

import com.exchanger.currency.domain.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepositoryJPA extends JpaRepository<Currency, Long> {
    List<Currency> findAllByDateLessThanEqualAndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT c.date FROM Currency c")
    List<String> findDistinctDates();

    List<Currency> findAllByDate(LocalDate date);
    @Query("")
    List<Currency> findAllByCodeAndDateLessThanEqualAndDateGreaterThanEqual(String code,
                                                                            LocalDate startDate,
                                                                            LocalDate endDate);
}
