package com.exchanger.ExchangerApp;

import com.exchanger.ExchangerApp.currency.domain.CurrencyDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
public class ExchangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangerApplication.class, args);
    }
}
