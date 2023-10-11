package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.currency.Currency;

public record CurrencyReportCurrency(String name, String code, Double mid) {
    public static CurrencyReportCurrency from(Currency currency){
        return new CurrencyReportCurrency(currency.getCurrency(),
                currency.getCode(),
                currency.getMid());
    }
}
