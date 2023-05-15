package com.exchanger.currency.domain.currency;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DateChecker {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String COUNT_CURRENCY_BY_DATE_QUERY = "SELECT count(*) FROM Currency where date = :date";

    public DateChecker(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ifInDatabase(LocalDate actualizationDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", actualizationDate);
        Integer result = jdbcTemplate.queryForObject(COUNT_CURRENCY_BY_DATE_QUERY, paramMap, Integer.class);
        return result != null && result != 0;
    }
}

