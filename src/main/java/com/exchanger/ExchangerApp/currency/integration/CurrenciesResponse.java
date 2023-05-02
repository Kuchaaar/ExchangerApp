package com.exchanger.ExchangerApp.currency.integration;

import java.util.Date;
import java.util.List;

public record CurrenciesResponse(String effectiveDate,List<CurrencyResponse> rates) { }
