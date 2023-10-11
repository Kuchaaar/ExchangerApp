package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.exceptions.NoDataException;

import java.util.List;

public class CurrencyReportDatasource {
    private final List<CurrencyReportCurrencies> currencies;
    private final Double averageMidPrice;

    public CurrencyReportDatasource(List<CurrencyReportCurrencies> currencies) {
        if (currencies.isEmpty()) {
            throw new NoDataException();
        }
        this.currencies = currencies;
        this.averageMidPrice = currencies.stream()
                .mapToDouble(CurrencyReportCurrencies::mid)
                .average()
                .orElseThrow();
    }

    public CurrencyReportCurrencies currencyByIndex(int index) {
        return currencies.get(index);
    }

    public int dataSourceSize() {
        return currencies.size();
    }

    public Double averageMidPrice() {
        return averageMidPrice;
    }
}