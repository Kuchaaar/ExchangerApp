package com.exchanger.ExchangerApp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.exchanger.ExchangerApp.currency.integration.CurrenciesResponse;
import com.exchanger.ExchangerApp.currency.integration.Currency;
import com.exchanger.ExchangerApp.currency.integration.CurrencyRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangerApplicationTests {
	private CurrencyRepo currencyRepo;
	@Before
	public void setUp() {
		currencyRepo = new CurrencyRepo();
	}

	@Test
	public void testSaveAll() {
		List<CurrenciesResponse> list = List.of(
				new CurrenciesResponse(List.of(
						new Currency("US Dollar", "USD", 1.0),
						new Currency("Euro", "EUR", 0.83)
				)),
				new CurrenciesResponse(List.of(
						new Currency("Japanese Yen", "JPY", 109.12),
						new Currency("British Pound", "GBP", 0.72)
				))
		);
		currencyRepo.saveAll(list);

		assertEquals(4, currencyRepo.currencyMap.size());
		assertEquals("USD", currencyRepo.currencyMap.get("USD").code());
		assertEquals("GBP", currencyRepo.currencyMap.get("GBP").code());
		assertEquals(0.83, currencyRepo.currencyMap.get("EUR").mid(), 0.001);
		assertEquals(109.12, currencyRepo.currencyMap.get("JPY").mid(), 0.001);
	}

}
