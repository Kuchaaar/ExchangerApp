package com.exchanger.ExchangerApp.currency.integration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.*;
@Component
public class CurrencyRepo implements CurrencyMap {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate ;
    private static final String insertIntoSql = "INSERT INTO Currency (currency,code,mid) VALUES (?,?,?)";

    public CurrencyRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveAll(List<CurrenciesResponse> list) {
        list.stream()
                .map(CurrenciesResponse::rates)
                .flatMap(Collection::stream)
                .forEach(currency -> currencyMap.put(currency.code(), currency));
    }
}
