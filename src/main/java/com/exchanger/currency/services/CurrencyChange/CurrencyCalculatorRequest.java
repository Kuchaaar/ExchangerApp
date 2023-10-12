package com.exchanger.currency.services.CurrencyChange;

import java.time.LocalDate;

public record CurrencyCalculatorRequest(LocalDate startDate, LocalDate endDate, int number) {
}