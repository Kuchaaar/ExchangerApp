package com.exchanger.ExchangerApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangerApplicationTests {

    @Test
    void testCurrencyUpdater(){
        CurrencyUpdaterTest currencyUpdaterTest = new CurrencyUpdaterTest();
        currencyUpdaterTest.setup();
        currencyUpdaterTest.updateWithTopCountTest();
        currencyUpdaterTest.updateWithoutTopCountTest();
    }

    @Test
    void testHolidaysUpdater(){
        HolidaysUpdaterTest holidaysUpdaterTest = new HolidaysUpdaterTest();
        holidaysUpdaterTest.setup();
        holidaysUpdaterTest.updateTest();
    }

}
