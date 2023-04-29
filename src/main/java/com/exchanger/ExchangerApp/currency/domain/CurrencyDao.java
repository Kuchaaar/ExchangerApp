package com.exchanger.ExchangerApp.currency.domain;
import com.exchanger.ExchangerApp.currency.integration.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class CurrencyDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String insertIntoSql = "INSERT INTO Currency VALUES(?,?,?)";
    @Autowired
    public CurrencyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Currency currency){
        jdbcTemplate.update(insertIntoSql,new Object[]{
                currency.currency(),
                currency.code(),
                currency.mid()
        });//jdbc named query, batchupdate, inversion of control
        //nie odpala sie schema.sql poczytać
        //
    }
}
