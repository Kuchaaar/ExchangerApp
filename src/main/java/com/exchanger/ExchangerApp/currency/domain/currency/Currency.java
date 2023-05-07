package com.exchanger.ExchangerApp.currency.domain.currency;

import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyResponse;

public record Currency(String currency, String code, double mid,String date) {

    public static Currency from(CurrencyResponse currencyResponse) {
        return new Currency(currencyResponse.currency(), currencyResponse.code(), currencyResponse.mid(), currencyResponse.date());
    }
}
