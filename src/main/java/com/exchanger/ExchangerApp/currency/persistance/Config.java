package com.exchanger.ExchangerApp.currency.persistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Config {
    private DataSource dataSource;
    @Autowired
    public Config(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }
}
