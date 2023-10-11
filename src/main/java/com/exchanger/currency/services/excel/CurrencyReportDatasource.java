package com.exchanger.currency.services.excel;

import java.util.List;

public class CurrencyReportDatasource{
    public List<CurrencyReportCurrency> currencies;
    public Double avg;
    public CurrencyReportDatasource(List<CurrencyReportCurrency> currencies){
        this.currencies = currencies;
        this.avg = currencies.stream()
                .mapToDouble(CurrencyReportCurrency::mid)
                .average()
                .orElseThrow();
    }
}