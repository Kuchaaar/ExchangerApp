package com.exchanger.currency.integration.currency;

import java.time.LocalDate;

public record CurrencyResponse(String currency, String code, double mid, LocalDate date) {
}