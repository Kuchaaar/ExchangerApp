package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.peristence.currency.DatabaseCurrencyRepository;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String DATA_CURRENCY_QUERY = "SELECT date from Currency WHERE date = :date";

    public DateChecker(DatabaseCurrencyRepository databaseCurrencyRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.databaseCurrencyRepository = databaseCurrencyRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ifInDatabase(){
        LocalDate localDate = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
    public boolean ifInDatabase(int days){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i=0;i<days;i++){
            LocalDate localDate1 = LocalDate.now().minusDays(i);
            String formattedDate1 = localDate1.format(formatter);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("date",formattedDate1);
            List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
            if (result.isEmpty()) {
                return false;
            }
            result.clear();
            paramMap.clear();
        }
        return true;
    }
}
