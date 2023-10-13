package com.exchanger.currency.services.currencychange;

import java.time.LocalDate;

public record FindCurrencyWithHighestRatePercentageChangeRequest(LocalDate startDate, LocalDate endDate, int number) {
}