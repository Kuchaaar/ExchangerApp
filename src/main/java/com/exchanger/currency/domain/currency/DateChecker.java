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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String COUNT_CURRENCY_BY_DATE_QUERY = "SELECT count(*) FROM Currency where date = :date";

    public DateChecker(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ifInDatabase(LocalDate actualizationDate) {
        String formattedDate = actualizationDate.format(DateTimeFormatter.ISO_DATE);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", formattedDate);
        Integer result = jdbcTemplate.queryForObject(COUNT_CURRENCY_BY_DATE_QUERY, paramMap, Integer.class);
        return result != null && result != 0;
    }

//    public boolean daysWithoutCurrencyDate(int days){
//        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        for(int i=0;i<days;i++){
//            LocalDate localDate1 = LocalDate.now().minusDays(i);
//            String formattedDate1 = localDate1.format(formatter);
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("date",formattedDate1);
//            Integer result = jdbcTemplate.queryForObject(COUNT_CURRENCY_BY_DATE_QUERY,paramMap, Integer.class);
//            if (result ==null || result == 0) {
//                return false;
//            }
//            result = 0;
//            paramMap.clear();
//        }
//        return true;
//    }
}

