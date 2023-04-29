package com.exchanger.ExchangerApp.currency.integration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Component
public class CurrencyRepo {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate ;
    private static final String insertIntoSql = "INSERT INTO Currency (currency,code,mid) VALUES (?,?,?)";

    public CurrencyRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int[] saveAll(List<CurrenciesResponse> list) {
        list.stream()
                .map(CurrenciesResponse::rates)
                .flatMap(Collection::stream)
                .forEach(currency -> currencyMap.put(currency.code(), currency));
        return this.jdbcTemplate.batchUpdate(insertIntoSql,
                new BatchPreparedStatementSetter(){
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        List<Currency> currencies = new ArrayList<>(currencyMap.values());
                        Currency currency = currencies.get(i);
                        ps.setString(1,currency.currency());
                        ps.setString(2,currency.code());
                        ps.setDouble(3,currency.mid());
                    }
                    public int getBatchSize() {
                        return currencyMap.size();
                    }
                });
    }
}
