package com.exchanger.ExchangerApp.currency.integration;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyDatabase implements CurrencyMap {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate ;
    private final CurrencyRepo currencyRepo;
    private static final String insertIntoSql = "INSERT INTO Currency (currency,code,mid) VALUES (?,?,?)";
    public CurrencyDatabase(DataSource dataSource, CurrencyRepo currencyRepo) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.currencyRepo = currencyRepo;
    }
    @Override
    public void saveAll(List<CurrenciesResponse> list) {
        this.jdbcTemplate.batchUpdate(insertIntoSql,
                new BatchPreparedStatementSetter(){
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        List<Currency> currencies = new ArrayList<>(currencyRepo.currencyMap.values());
                        Currency currency = currencies.get(i);
                        ps.setString(1,currency.currency());
                        ps.setString(2,currency.code());
                        ps.setDouble(3,currency.mid());
                    }
                    public int getBatchSize() {
                        return currencyRepo.currencyMap.size();
                    }
                });
    }
}
