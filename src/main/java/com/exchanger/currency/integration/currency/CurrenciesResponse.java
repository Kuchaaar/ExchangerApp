package com.exchanger.currency.integration.currency;

import java.util.List;

public record CurrenciesResponse(String effectiveDate,List<CurrencyResponse> rates) { }
