package com.exchanger.currency.services.currencychange;

import java.util.ArrayList;
import java.util.List;

public record FindCurrencyWithHighestRatePercentageChangeResponse(
        List<CurrencyCodeWithPercentage> currencyCodeWithPercentages,
        List<CurrencyCodeWithPercentageError> currencyCodeWithPercentageErrors) {


    public static FindCurrencyWithHighestRatePercentageChangeResponse from(List<CurrencyFromStartDateAndEndDate> currencyFromStartDateAndEndDates,
                                                                           int number){
        List<CurrencyCodeWithPercentage> currencyCodeWithPercentages = new ArrayList<>();
        List<CurrencyCodeWithPercentageError> globalCurrencyCodeWithPercentageErrors = new ArrayList<>();
        for(CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate : currencyFromStartDateAndEndDates){
            List<CurrencyCodeWithPercentageError> currencyCodeWithPercentageErrors =
                    aErrors(currencyFromStartDateAndEndDate);
            if(currencyCodeWithPercentageErrors.isEmpty()){
                currencyCodeWithPercentages.add(CurrencyCodeWithPercentage.from(currencyFromStartDateAndEndDate));
            }else{
                globalCurrencyCodeWithPercentageErrors.addAll(currencyCodeWithPercentageErrors);
            }
        }
        return new FindCurrencyWithHighestRatePercentageChangeResponse(numberOfCurrencyCodeWithPercentages(
                currencyCodeWithPercentages,
                number),
                globalCurrencyCodeWithPercentageErrors);
    }

    private static List<CurrencyCodeWithPercentageError> aErrors(CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate){
        List<CurrencyCodeWithPercentageError> currencyCodeWithPercentageErrors = new ArrayList<>();
        if(currencyFromStartDateAndEndDate.currencyFromStartDate() == null){
            currencyCodeWithPercentageErrors.add(new CurrencyCodeWithPercentageError(
                    currencyFromStartDateAndEndDate.currencyFromEndDate().getCode(),
                    "No data for StartDate for this Currency"
            ));
        }
        if(currencyFromStartDateAndEndDate.currencyFromEndDate() == null){
            currencyCodeWithPercentageErrors.add(new CurrencyCodeWithPercentageError(
                    currencyFromStartDateAndEndDate.currencyFromStartDate().getCode(),
                    "No data for EndDate for this Currency"
            ));
        }
        return currencyCodeWithPercentageErrors;
    }

    private static List<CurrencyCodeWithPercentage> numberOfCurrencyCodeWithPercentages(List<CurrencyCodeWithPercentage> currencyCodeWithPercentages,
                                                                                        int number){
        currencyCodeWithPercentages.sort((c1, c2) -> Double.compare(c2.percentageChange().doubleValue(),
                c1.percentageChange().doubleValue()));
        if(currencyCodeWithPercentages.size() < number){
            return currencyCodeWithPercentages;
        }else{
            return currencyCodeWithPercentages.subList(0, number);
        }
    }
}
