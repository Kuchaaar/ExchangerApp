package com.exchanger.ExchangerApp;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.services.currencychange.CurrencyCodeWithPercentage;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import com.exchanger.currency.services.currencychange.FindCurrencyWithHighestRatePercentageChangeResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindCurrencyWithHighestRatePercentageChangeResponseTest {
    private final LocalDate startDate1 = LocalDate.parse("2023-02-01");
    private final LocalDate startDate2 = LocalDate.parse("2023-02-01");
    private final LocalDate endDate1 = LocalDate.parse("2023-05-01");
    private final LocalDate endDate2 = LocalDate.parse("2023-05-01");
    private final Long id1 = 1L;
    private final Long id2 = 1L;
    private final String currency1 = "currency1";
    private final String currency2 = "currency2";
    private final String code1 = "code1";
    private final String code2 = "code2";
    private final BigDecimal mid1 = BigDecimal.valueOf(1);
    private final BigDecimal endMid1 = BigDecimal.valueOf(10);
    private final BigDecimal mid2 = BigDecimal.valueOf(10);
    private final BigDecimal endMid2 = BigDecimal.valueOf(8);
    private final CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate1 =
            new CurrencyFromStartDateAndEndDate(
                    new Currency(id1, currency1, code1, mid1, startDate1),
                    new Currency(id1, currency1, code1, endMid1, endDate1)
            );
    private final CurrencyFromStartDateAndEndDate currencyFromStartDateAndEndDate2 =
            new CurrencyFromStartDateAndEndDate(
                    new Currency(id2, currency2, code2, mid2, startDate2),
                    new Currency(id2, currency2, code2, endMid2, endDate2)
            );
    private final List<CurrencyFromStartDateAndEndDate> currencyFromStartDateAndEndDates1 =
            List.of(currencyFromStartDateAndEndDate1, currencyFromStartDateAndEndDate2);
    private final BigDecimal expectedDecimal1 = BigDecimal.valueOf(900).setScale(2, RoundingMode.UP);
    private final BigDecimal expectedDecimal2 = BigDecimal.valueOf(- 20).setScale(2,RoundingMode.UP);
    private final CurrencyCodeWithPercentage currencyCodeWithPercentage1 =
            new CurrencyCodeWithPercentage(code1, expectedDecimal1);
    private final CurrencyCodeWithPercentage currencyCodeWithPercentage2 =
            new CurrencyCodeWithPercentage(code2, expectedDecimal2);
    private final FindCurrencyWithHighestRatePercentageChangeResponse
            findCurrencyWithHighestRatePercentageChangeResponse =
            new FindCurrencyWithHighestRatePercentageChangeResponse(List.of(currencyCodeWithPercentage1,
                    currencyCodeWithPercentage2), List.of());

    @Test
    void from(){
        int number = 2;
        assertEquals(findCurrencyWithHighestRatePercentageChangeResponse, FindCurrencyWithHighestRatePercentageChangeResponse.from(currencyFromStartDateAndEndDates1,
                number));
    }
}