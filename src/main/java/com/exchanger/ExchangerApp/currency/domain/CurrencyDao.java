package com.exchanger.ExchangerApp.currency.domain;
import com.exchanger.ExchangerApp.currency.integration.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class CurrencyDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String insertIntoSql = "INSERT INTO Currency VALUES(?,?,?)";

    @Autowired
    public CurrencyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


        //jdbc named query, batchupdate, inversion of control

}
