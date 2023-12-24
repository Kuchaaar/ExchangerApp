package com.exchanger.ExchangerApp.web;

import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeRequest;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import com.exchanger.currency.web.CurrencyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyControllerTest{
    private final FindCurrencyWithHighestRatePercentageChangeRequest
            findCurrencyWithHighestRatePercentageChangeRequestTest =
            new FindCurrencyWithHighestRatePercentageChangeRequest(
                    LocalDate.of(2023, 11, 11), LocalDate.of(2023, 11, 12), 5);
    private final FindCurrencyWithHighestRatePercentageChangeResponse
            findCurrencyWithHighestRatePercentageChangeResponseTest =
            new FindCurrencyWithHighestRatePercentageChangeResponse(
                    List.of(), List.of());
    @Mock
    private CurrencyService currencyService;
    @Mock
    private CurrencyController currencyController;


    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCurrencyWithHighestRatePercentageChangeResponseTest(){
        when(currencyService.findCurrencyWithHighestRatePercentageChange(
                findCurrencyWithHighestRatePercentageChangeRequestTest)).thenReturn(
                findCurrencyWithHighestRatePercentageChangeResponseTest);
        assertEquals(findCurrencyWithHighestRatePercentageChangeResponseTest,
                currencyService.findCurrencyWithHighestRatePercentageChange(
                        findCurrencyWithHighestRatePercentageChangeRequestTest));
    }

    @Test
    void clearCacheTest(){
        currencyController.clearCache();
        verify(currencyController).clearCache();
    }
}
