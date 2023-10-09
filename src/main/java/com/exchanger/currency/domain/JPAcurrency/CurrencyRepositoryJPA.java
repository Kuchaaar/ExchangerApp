package com.exchanger.currency.domain.JPAcurrency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepositoryJPA extends JpaRepository<CurrencyJPA, Long> {
    @Query("SELECT c FROM CurrencyJPA c WHERE c.date BETWEEN :start AND :end")
    List<CurrencyJPA> findEntitiesBetweenDates(LocalDate start, LocalDate end);

    @Query("SELECT DISTINCT c.date FROM CurrencyJPA c")
    List<String> findDistinctDates();
}
