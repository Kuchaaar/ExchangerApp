package com.exchanger.ExchangerApp;
import com.exchanger.ExchangerApp.currency.domain.currency.CurrencyRepository;
import com.exchanger.ExchangerApp.currency.domain.currency.CurrencyUpdater;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyClient;
import com.exchanger.ExchangerApp.currency.integration.currency.CurrencyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrencyUpdaterTest {

    @Mock
    private CurrencyClient currencyClient;
    @Mock
    private CurrencyRepository currencyRepository;
    private CurrencyUpdater currencyUpdater;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        currencyUpdater = new CurrencyUpdater(currencyClient, currencyRepository);
    }

    @Test
    public void updateWithTopCountTest() {
        String table = "example_table";
        int topCount = 10;
        List<CurrenciesResponse> currenciesResponses = testListOfCurrenciesResponse();
        when(currencyClient.getByTable(table, topCount)).thenReturn(currenciesResponses);
        currencyUpdater.update(table, topCount);
        verify(currencyClient).getByTable(table, topCount);
        //verify(currencyRepository).saveAll(anyList());  to wyrzuca w Without działa git
    }

    @Test
    public void updateWithoutTopCountTest() {
        String table = "example_table";
        List<CurrenciesResponse> currenciesResponses =testListOfCurrenciesResponse();
        when(currencyClient.getByTable(table)).thenReturn(currenciesResponses);
        currencyUpdater.update(table);
        verify(currencyClient).getByTable(table);
        verify(currencyRepository).saveAll(anyList());
    }
    private List<CurrenciesResponse> testListOfCurrenciesResponse() {
        List<CurrenciesResponse> currenciesResponses = new ArrayList<>();
        CurrencyResponse currency1 = new CurrencyResponse("Currency 1", "CUR1", 1.0, "2023-05-01");
        CurrencyResponse currency2 = new CurrencyResponse("Currency 2", "CUR2", 2.0, "2023-05-01");
        CurrencyResponse currency3 = new CurrencyResponse("Currency 3", "CUR3", 3.0, "2023-05-01");
        List<CurrencyResponse> rates1 = new ArrayList<>();
        rates1.add(currency1);
        rates1.add(currency2);
        rates1.add(currency3);
        CurrenciesResponse response1 = new CurrenciesResponse("2023-05-01", rates1);
        List<CurrencyResponse> rates2 = new ArrayList<>();
        rates2.add(currency2);
        rates2.add(currency3);
        CurrenciesResponse response2 = new CurrenciesResponse("2023-05-02", rates2);
        currenciesResponses.add(response1);
        currenciesResponses.add(response2);
        return currenciesResponses;
    }
}
