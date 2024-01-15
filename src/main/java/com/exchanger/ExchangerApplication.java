package com.exchanger;

import com.exchanger.currency.config.security.cors.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties(CorsProperties.class)
public class ExchangerApplication{
    public static void main(String[] args){
        SpringApplication.run(ExchangerApplication.class, args);
    }
}