package com.exchanger.currency.services.currencychange;

import java.util.List;

public record FindCurrencyWithHighestRatePercentageChangeResponse(
        List<CurrencyCodeWithPercentage> currencyCodeWithPercentages,
        List<CurrencyCodeWithPercentageError> currencyCodeWithPercentageErrors) {
}
