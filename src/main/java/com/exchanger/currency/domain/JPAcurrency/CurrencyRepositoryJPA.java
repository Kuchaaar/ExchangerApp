package com.exchanger.currency.domain.JPAcurrency;

import com.exchanger.currency.domain.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepositoryJPA extends JpaRepository<Currency, Long> {
    List<Currency> findAllByDateLessThanEqualAndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT c.date FROM CurrencyJPA c")
    List<String> findDistinctDates();

    List<Currency> findAllByDate(LocalDate date);

    List<Currency> findAllByCodeAndDateLessThanEqualAndDateGreaterThanEqual(String code,
                                                                            LocalDate date1,
                                                                            LocalDate date2);
}
