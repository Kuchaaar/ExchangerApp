package com.exchanger.ExchangerApp.currency.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyDatabase implements CurrencyMap {

    private final JdbcTemplate jdbcTemplate;
    private final CurrencyRepo currencyRepo;
    private static final String insertIntoSql = "INSERT INTO Currency (currency,code,mid) VALUES (?,?,?)";

    @Autowired
    public CurrencyDatabase(CurrencyRepo currencyRepo, DataSource dataSource) {
        this.currencyRepo = currencyRepo;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveAll(List<CurrenciesResponse> list) {
        jdbcTemplate.batchUpdate(insertIntoSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        int index = 0;
                        for (Map.Entry<String, List<Currency>> entry : currencyRepo.currencyMap.entrySet()) {
                            String code = entry.getKey();
                            List<Currency> currencies = entry.getValue();
                            for (Currency currency : currencies) {
                                ps.setString(1, currency.currency());
                                ps.setString(2, code);
                                ps.setDouble(3, currency.mid());
                                ps.addBatch();
                                index++;
                            }
                        }
                    }

                    public int getBatchSize() {
                        int count = 0;
                        for (List<Currency> currencies : currencyRepo.currencyMap.values()) {
                            count += currencies.size();
                        }
                        return count;
                    }
                });
    }
}
