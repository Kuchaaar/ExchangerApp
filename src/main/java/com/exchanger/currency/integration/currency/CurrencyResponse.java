package com.exchanger.currency.integration.currency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CurrencyResponse(String currency, String code, BigDecimal mid, LocalDate date) {
}