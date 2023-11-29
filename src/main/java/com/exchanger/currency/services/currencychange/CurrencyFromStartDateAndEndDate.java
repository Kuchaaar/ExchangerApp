package com.exchanger.currency.services.currencychange;

import com.exchanger.currency.domain.currency.Currency;

public record CurrencyFromStartDateAndEndDate(Currency currencyFromStartDate, Currency currencyFromEndDate) {
}