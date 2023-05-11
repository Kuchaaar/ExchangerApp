package com.exchanger.ExchangerApp;
import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyUpdater;
import com.exchanger.currency.integration.currency.CurrenciesResponse;
import com.exchanger.currency.integration.currency.CurrencyClient;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.peristence.currency.InMemoryCurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Comparator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CurrencyUpdaterTest {

    private static final String EFFECTIVE_DATE_1 = "2023-05-01";
    private static final String EFFECTIVE_DATE_2 = "2023-05-02";
    private static final String CURRENCY_2 = "Currency 2";
    private static final String CURRENCY_CODE_2 = "CUR2";
    private static final String CURRENCY_3 = "Currency 3";
    private static final String CURRENCY_CODE_3 = "CUR3";
    private static final String CURRENCY_1 = "Currency 1";
    private static final double MID_2 = 2.0;
    private static final double MID_3 = 3.0;
    private static final double MID = 1.0;
    private static final String CURRENCY_CODE_1 = "CUR1";
    @Mock
    private CurrencyClient currencyClient;
    private InMemoryCurrencyRepository currencyRepository;
    private CurrencyUpdater currencyUpdater;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        currencyRepository = new InMemoryCurrencyRepository();
        currencyUpdater = new CurrencyUpdater(currencyClient, currencyRepository);
    }

    @Test
    public void updateWithTopCountTest() {
//      given:
        String table = "example_table";
        int topCount = 10;
        when(currencyClient.getByTable(table, topCount)).thenReturn(mockCurrenciesResponse());
//      when:
        currencyUpdater.update(table, topCount);
//      then:
        assertEquals(sortedByCurrencyName(currencyRepository.findAll()), sortedByCurrencyName(List.of(
                aCurrency(CURRENCY_1, CURRENCY_CODE_1, MID, EFFECTIVE_DATE_1),
                aCurrency(CURRENCY_2, CURRENCY_CODE_2, MID_2, EFFECTIVE_DATE_2),
                aCurrency(CURRENCY_3, CURRENCY_CODE_3, MID_3, EFFECTIVE_DATE_2)
        )));
    }

    private List<Currency> sortedByCurrencyName(List<Currency> currencies) {
        return currencies.stream()
                .sorted(Comparator.comparing(Currency::currency))
                .toList();
    }

    @Test
    public void updateWithoutTopCountTest() {
//      given
        String table = "example_table";
        when(currencyClient.getByTable(table)).thenReturn(mockCurrenciesResponse());
//      when
        currencyUpdater.update(table);
//      then
        assertEquals(sortedByCurrencyName(currencyRepository.findAll()), sortedByCurrencyName(List.of(
                aCurrency(CURRENCY_1, CURRENCY_CODE_1, MID, EFFECTIVE_DATE_1),
                aCurrency(CURRENCY_2, CURRENCY_CODE_2, MID_2, EFFECTIVE_DATE_2),
                aCurrency(CURRENCY_3, CURRENCY_CODE_3, MID_3, EFFECTIVE_DATE_2)
        )));
    }

    private List<CurrenciesResponse> mockCurrenciesResponse() {
        CurrencyResponse currency2 = aCurrencyResponse(CURRENCY_2, CURRENCY_CODE_2, MID_2);
        CurrencyResponse currency3 = aCurrencyResponse(CURRENCY_3, CURRENCY_CODE_3, MID_3);
        CurrenciesResponse response1 = aCurrenciesResponse(
                EFFECTIVE_DATE_1,
                List.of(
                        aCurrencyResponse(CURRENCY_1, CURRENCY_CODE_1, MID),
                        currency2,
                        currency3));
        CurrenciesResponse response2 = aCurrenciesResponse(
                EFFECTIVE_DATE_2,
                List.of(
                        currency2,
                        currency3));
        return List.of(response1, response2);
    }

    private static CurrenciesResponse aCurrenciesResponse(String effectiveDate, List<CurrencyResponse> currencyResponses) {
        return new CurrenciesResponse(effectiveDate, currencyResponses);
    }

    private static CurrencyResponse aCurrencyResponse(String currency, String code, double mid) {
        return new CurrencyResponse(currency, code, mid, EFFECTIVE_DATE_1);
    }

    private Currency aCurrency(String currency, String code, double mid, String date) {
        return new Currency(currency, code, mid, date);
    }
}
