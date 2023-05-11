package com.exchanger.ExchangerApp.currency.domain.currency;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class CurrencyReader2 {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String DATA_CURRENCY_QUERY = "SELECT * from Currency WHERE date = :date";

    public CurrencyReader2(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void WriteData(int topCount){
        for(int i=0;i<topCount;i++) {
            LocalDate localDate = LocalDate.now().minusDays(i);
            String formattedDate = localDate.format(FORMATTER);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("date",formattedDate);
            List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
            result.forEach(System.out::println);
        }
    }
    public void WriteData(){
        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(FORMATTER);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", formattedDate);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
        result.forEach(System.out::println);
    }
}