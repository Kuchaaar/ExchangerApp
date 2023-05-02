package com.exchanger.ExchangerApp.currency.integration;

import java.util.Date;

public record CurrencyResponse(String currency, String code, double mid) {
}
