package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.exceptions.NoDataException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class CurrencyReportDatasource {
    private final List<CurrencyReportCurrencies> currencies;
    private final BigDecimal averageMidPrice;

    public CurrencyReportDatasource(List<CurrencyReportCurrencies> currencies){
        if(currencies.isEmpty()){
            throw new NoDataException();
        }
        this.currencies = currencies;
        this.averageMidPrice =
                average(getMid(currencies));
    }

    private List<BigDecimal> getMid(List<CurrencyReportCurrencies> currencies){
        return currencies.stream()
                .map(CurrencyReportCurrencies::mid)
                .toList();
    }

    private BigDecimal average(List<BigDecimal> bigDecimals){
        if(bigDecimals.isEmpty()){
            throw new NoDataException();
        }
        BigDecimal sum = bigDecimals.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(bigDecimals.size()), RoundingMode.UP);
    }

    public CurrencyReportCurrencies currencyByIndex(int index){
        if(currencies.size() <=index){
            return null;
        }
        return currencies.get(index);
    }

    public int dataSourceSize(){
        return currencies.size();
    }

    public BigDecimal averageMidPrice(){
        return averageMidPrice;
    }
}