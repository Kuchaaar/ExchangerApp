package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.currency.Currency;

public record CurrencyReportCurrencies(String name, String code, Double mid) {
    public static CurrencyReportCurrencies from(Currency currency){
        return new CurrencyReportCurrencies(currency.getCurrency(),
                currency.getCode(),
                currency.getMid());
    }
}
