package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.peristence.DatabaseCurrencyRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class CurrencyReader2 {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private Map<String, Object> paramMap = new HashMap<>();
    private LocalDate localDate = LocalDate.now();
    private String formattedDate = localDate.format(formatter);
    private DatabaseCurrencyRepository databaseCurrencyRepository;
    private static final String DATA_CURRENCY_QUERY = "SELECT * from Currency WHERE date = :date";

    public CurrencyReader2(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void WriteData(int topCount){
        for(int i=0;i<topCount;i++) {
            LocalDate localDate1 = LocalDate.now().minusDays(i);
            String formattedDate1 = localDate1.format(formatter);
            paramMap.put("date",formattedDate1);
            List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
            result.forEach(System.out::println);
        }
    }
    public void WriteData(){
        paramMap.put("date", formattedDate);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
        result.forEach(System.out::println);
    }
}
