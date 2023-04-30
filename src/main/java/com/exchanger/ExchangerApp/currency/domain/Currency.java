package com.exchanger.ExchangerApp.currency.domain;

import com.exchanger.ExchangerApp.currency.integration.CurrencyResponse;

public record Currency(String currency, String code, double mid) {

    public static Currency from(CurrencyResponse currencyResponse) {
        return new Currency(currencyResponse.currency(), currencyResponse.code(), currencyResponse.mid());
    }
}
