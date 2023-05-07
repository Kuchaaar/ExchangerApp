package com.exchanger.ExchangerApp.currency.integration.currency;

import java.util.List;

public record CurrenciesResponse(String effectiveDate,List<CurrencyResponse> rates) { }
