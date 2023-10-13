package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.currency.Currency;

import java.math.BigDecimal;

public record CurrencyReportCurrencies(String name, String code, BigDecimal mid) {
    public static CurrencyReportCurrencies from(Currency currency){
        return new CurrencyReportCurrencies(currency.getCurrency(),
                currency.getCode(),
                currency.getMid());
    }
}
