package com.exchanger.currency.services.currencychange;

import java.math.BigDecimal;

public record CurrencyCodeWithPercentage(String currencyCode, BigDecimal percentageChange){
}
