package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.integration.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public CurrencyDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public void  add(Currency currency){
        String sql = "INSERT INTO Currency VALUES(?,?,?)";
        jdbcTemplate.update(sql,new Object[]{
                currency.currency(),
                currency.code(),
                currency.mid()
        });// https://www.baeldung.com/spring-jdbc-batch-inserts
    }
// https://www.baeldung.com/spring-jdbc-batch-inserts

}
