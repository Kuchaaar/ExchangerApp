package com.exchanger.ExchangerApp.domain.currency;

import com.exchanger.currency.domain.currency.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTest{
    private final Currency currency = new Currency("name", "code", BigDecimal.valueOf(1), LocalDate.of(2023, 11, 11));

    @Test
    void currencyCurrencyTest(){
        currency.setCurrency("name2");
        assertEquals("name2", currency.getCurrency());
    }
    @Test
    void currencyCodeTest(){
        currency.setCode("code2");
        assertEquals("code2", currency.getCode());
    }
    @Test
    void currencyMidTest(){
        currency.setMid(BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(2), currency.getMid());
    }
    @Test
    void currencyDateTest(){
        currency.setDate(LocalDate.of(1,1,1));
        assertEquals(LocalDate.of(1,1,1),currency.getDate());
    }
    @Test
    void currencyEqualsTest(){
        Currency currency1 = currency;
        assertEquals(true,currency.equals(currency1));

        String currency2 = "test";
        assertEquals(false,currency.equals(currency2));
    }
    @Test
    void currencyToStringTest(){
        String test = "Currency{" +
                "id=null" +
                ", currency='name"+ '\'' +
                ", code='code"+ '\'' +
                ", mid=1"+
                ", date=2023-11-11"+
                '}';
        assertEquals(test,currency.toString());
    }
}
