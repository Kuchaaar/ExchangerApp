package com.exchanger.currency.services.currencychange;


import java.math.BigDecimal;
import java.math.RoundingMode;

public record CurrencyCodeWithPercentage(String currencyCode, BigDecimal percentageChange){

    public static CurrencyCodeWithPercentage from(CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate){
        return new CurrencyCodeWithPercentage(currencyFromStartDateAndEndDate.currencyFromStartDate().getCode(),
                getPercentageChange(currencyFromStartDateAndEndDate)
                );
    }
    private static BigDecimal getPercentageChange(CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate){
        return (currencyFromStartDateAndEndDate.currencyFromEndDate().getMid()
                .multiply(BigDecimal.valueOf(100.0))
                .divide(currencyFromStartDateAndEndDate.currencyFromStartDate().getMid(), RoundingMode.UP)
                .subtract((BigDecimal.valueOf(100.0))))
                .setScale(2,RoundingMode.UP);
    }

}
