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
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String COUNT_CURRENCY_BY_DATE_QUERY = "SELECT count(date) FROM Currency where date = :date";

    public DateChecker(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ifInDatabase(){
        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(formatter);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", formattedDate);
        Integer result = jdbcTemplate.queryForObject(COUNT_CURRENCY_BY_DATE_QUERY,paramMap, Integer.class);
        if (result==0 || result == null) {
            return false;
        }
        else{
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
            Integer result = jdbcTemplate.queryForObject(COUNT_CURRENCY_BY_DATE_QUERY,paramMap, Integer.class);
            if (result ==0 || result == null) {
                return false;
            }
            result = 0;
            paramMap.clear();
        }
        return true;
    }
}
