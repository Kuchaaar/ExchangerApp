package com.exchanger.ExchangerApp.currency.integration;

import java.util.List;

public record CurrenciesResponse(List<CurrencyResponse> rates) { }
