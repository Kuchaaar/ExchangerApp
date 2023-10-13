package com.exchanger.currency.services.currencychange;

import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.peristence.currency.InMemoryCurrencyRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyChangeServiceTest {

    @Test
    void findCurrencyWithHighestRatePercentageChange(){
        //given
        final InMemoryCurrencyRepository inMemoryCurrencyRepository = new InMemoryCurrencyRepository();
        final CurrencyChangeService currencyChangeService = new CurrencyChangeService(inMemoryCurrencyRepository);
        List<CurrencyResponse> currencyResponses =
                List.of(new CurrencyResponse("name", "code", BigDecimal.valueOf(100.0), LocalDate.of(2023, 10, 10)),
                        new CurrencyResponse("name", "code", BigDecimal.valueOf(200.0), LocalDate.of(2023, 10, 11)),
                        new CurrencyResponse("name2", "code2", BigDecimal.valueOf(100.0), LocalDate.of(2023, 10, 10)),
                        new CurrencyResponse("name2", "code2", BigDecimal.valueOf(115.0), LocalDate.of(2023, 10, 11)),
                        new CurrencyResponse("name3", "code3", BigDecimal.valueOf(21.0), LocalDate.of(2023, 10, 10)),
                        new CurrencyResponse("name4", "code4", BigDecimal.valueOf(37.0), LocalDate.of(1939, 9, 18))
                );
        inMemoryCurrencyRepository.saveAll(currencyResponses);

        //when
        FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponse =
                currencyChangeService.findCurrencyWithHighestRatePercentageChange(new FindCurrencyWithHighestRatePercentageChangeRequest(
                        LocalDate.of(2023, 10, 10),
                        LocalDate.of(2023, 10, 11),
                        2));
        //then
        FindCurrencyWithHighestRatePercentageChangeResponse findCurrencyWithHighestRatePercentageChangeResponseTest =
                new FindCurrencyWithHighestRatePercentageChangeResponse(
                        List.of(new CurrencyCodeWithPercentage("code", BigDecimal.valueOf(100.00)),
                                new CurrencyCodeWithPercentage("code2", BigDecimal.valueOf(15.00))),
                        List.of());
        assertEquals(findCurrencyWithHighestRatePercentageChangeResponseTest,
                findCurrencyWithHighestRatePercentageChangeResponse);
    }
}