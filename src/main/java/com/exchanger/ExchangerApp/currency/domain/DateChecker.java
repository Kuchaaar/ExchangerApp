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
    private LocalDate localDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String formattedDate = localDate.format(formatter);
    private Map<String, Object> paramMap = new HashMap<>();
    private static final String DATA_CURRENCY_QUERY = "SELECT date from Currency WHERE date = :date";

    public DateChecker(DatabaseCurrencyRepository databaseCurrencyRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.databaseCurrencyRepository = databaseCurrencyRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ifInDatabase(){
        paramMap.put("date", formattedDate);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(DATA_CURRENCY_QUERY, paramMap);
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public boolean ifInDatabase(int days){
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
