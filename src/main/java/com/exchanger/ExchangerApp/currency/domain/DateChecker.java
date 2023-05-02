package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.Holidays.HolidaysReader;
import com.exchanger.ExchangerApp.currency.Holidays.HolidaysResponse;
import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;
import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import de.jollyday.Holiday;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DateChecker {
    private final DatabaseCurrencyRepository databaseCurrencyRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String DATA_CURRENCY_QUERY = "SELECT date from Currency WHERE date = :date";

    public DateChecker(DatabaseCurrencyRepository databaseCurrencyRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.databaseCurrencyRepository = databaseCurrencyRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ifInDatabase(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDate.format(formatter);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", formattedDate);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }


    }
}