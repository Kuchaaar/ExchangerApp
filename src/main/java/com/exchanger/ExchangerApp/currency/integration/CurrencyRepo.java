package com.exchanger.ExchangerApp.currency.integration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyRepo {
    private final Map<String, Currency> currencyMap = new HashMap<>();
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate ;
    private static final String insertIntoSql = "INSERT INTO Currency VALUES(?,?,?)";

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
                        ps.setString(1,currencyMap.get(i).currency());
                        ps.setString(2,currencyMap.get(i).code());
                        ps.setDouble(3,currencyMap.get(i).mid());
                    }
                    public int getBatchSize() {
                        return currencyMap.size();
                    }
                });
    }
}
