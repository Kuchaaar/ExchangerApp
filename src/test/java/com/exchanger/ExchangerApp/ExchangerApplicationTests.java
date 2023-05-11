package com.exchanger.ExchangerApp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExchangerApplicationTests {

	@Test
	public void testCurrencyUpdater() {
		CurrencyUpdaterTest currencyUpdaterTest = new CurrencyUpdaterTest();
		currencyUpdaterTest.setup();
		currencyUpdaterTest.updateWithTopCountTest();
		currencyUpdaterTest.updateWithoutTopCountTest();
	}
	@Test
	public void testHolidaysUpdater(){
		HolidaysUpdaterTest holidaysUpdaterTest = new HolidaysUpdaterTest();
		holidaysUpdaterTest.setup();
		holidaysUpdaterTest.updateTest();
	}

}
