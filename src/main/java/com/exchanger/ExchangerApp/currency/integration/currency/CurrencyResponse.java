package com.exchanger.ExchangerApp.currency.integration.currency;

import java.util.Date;

public record CurrencyResponse(String currency, String code, double mid,String date) {
}
