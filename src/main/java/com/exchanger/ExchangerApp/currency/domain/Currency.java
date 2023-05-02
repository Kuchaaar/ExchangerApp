package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;

import java.util.Date;

public record Currency(String currency, String code, double mid,String date) {

    public static Currency from(CurrencyResponse currencyResponse) {
        return new Currency(currencyResponse.currency(), currencyResponse.code(), currencyResponse.mid(), currencyResponse.date());
    }
}
