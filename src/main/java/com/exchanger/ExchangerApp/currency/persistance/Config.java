package com.exchanger.ExchangerApp.currency.persistance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class Config {
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    public void DatabaseInit(){
        String sql = "CREATE TABLE Currency(currency_id int AUTO_INCREMENT,currency varchar(255)," +
                " code varchar(4), mid double, PRIMARY KEY (currency_id)) " ;
        getJdbcTemplate().update(sql);
    }
}
