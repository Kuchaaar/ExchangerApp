package com.exchanger.ExchangerApp.currency.jdbc;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Config {
    String url2 = "jdbc:mysql://127.0.0.1:3307/exchanger";
    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url2);
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("root");
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        return dataSourceBuilder.build();
    }
    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    public void DatabaseInit(){
        String sql = "CREATE TABLE Currency(currency_id int AUTO_INCREMENT,currency varchar(255), code varchar(4), mid double, PRIMARY KEY (currency_id)) " ;
        getJdbcTemplate().update(sql);
    }
}
