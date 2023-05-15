package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;

import java.time.LocalDate;

public record Currency(String currency, String code, double mid, LocalDate date) {

    public static Currency from(CurrencyResponse currencyResponse) {
        return new Currency(currencyResponse.currency(), currencyResponse.code(), currencyResponse.mid(), currencyResponse.date());
    }
}
