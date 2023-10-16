package com.exchanger.currency.services.currencychange;

import java.util.ArrayList;
import java.util.List;

public record FindCurrencyWithHighestRatePercentageChangeResponse(
        List<CurrencyCodeWithPercentage> currencyCodeWithPercentages,
        List<CurrencyCodeWithPercentageError> currencyCodeWithPercentageErrors) {


    public static FindCurrencyWithHighestRatePercentageChangeResponse from(List<CurrencyFromStartDateAndEndDate> currencyFromStartDateAndEndDates){
        List<CurrencyCodeWithPercentage> currencyCodeWithPercentages = new ArrayList<>();
        List<CurrencyCodeWithPercentageError> currencyCodeWithPercentageErrors = new ArrayList<>();
        for(CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate : currencyFromStartDateAndEndDates){
            if(currencyFromStartDateAndEndDate.currencyFromEndDate() == null){
                currencyCodeWithPercentageErrors.add(new CurrencyCodeWithPercentageError(
                        currencyFromStartDateAndEndDate.currencyFromStartDate().getCode(),
                        "No data for EndDate for this currency"));
            }else if(currencyFromStartDateAndEndDate.currencyFromStartDate() == null){
                currencyCodeWithPercentageErrors.add(new CurrencyCodeWithPercentageError(
                        currencyFromStartDateAndEndDate.currencyFromEndDate().getCode(),
                        "No data for StartDate for this currency"));
            }else{
                currencyCodeWithPercentages.add(CurrencyCodeWithPercentage.from(currencyFromStartDateAndEndDate));
            }
        }
        return new FindCurrencyWithHighestRatePercentageChangeResponse(currencyCodeWithPercentages,
                currencyCodeWithPercentageErrors);
    }
}
