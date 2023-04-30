package com.exchanger.ExchangerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangerApplication.class, args);
    }
}
