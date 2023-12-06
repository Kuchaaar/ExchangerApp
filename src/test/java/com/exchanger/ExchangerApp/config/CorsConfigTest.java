package com.exchanger.ExchangerApp.config;

import com.exchanger.currency.config.CorsConfig;
import com.exchanger.currency.config.CorsProperties;
import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.web.CurrencyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

@WebMvcTest({CurrencyController.class, CorsConfig.class,
        CorsProperties.class})
class CorsConfigTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testCorsConfig() throws Exception{
        applicationContext.getBeanDefinitionCount();
        //when
        MvcResult mvcResult = mockMvc.perform(options("/available_codes")
                        .header("Origin", "http://google.com")
                        .header("Access-Control-Request-Method", "OPTIONS")
                        .header("Access-Control-Request-Headers", "*"))
                .andReturn();
        //then
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("http://google.com", mvcResult.getResponse().getHeader("Access-Control-Allow-Origin"));
        assertEquals("GET,POST,PUT,DELETE,OPTIONS", mvcResult.getResponse().getHeader("Access-Control-Allow-Methods"));
        assertEquals("*", mvcResult.getResponse().getHeader("Access-Control-Allow-Headers"));
        assertEquals(Boolean.TRUE.toString(), mvcResult.getResponse().getHeader("Access-Control-Allow-Credentials"));
    }

    @Test
    void testCorsConfigWithBadOrigin() throws Exception{
        //when
        MvcResult mvcResult1 = mockMvc.perform(options("/available_codes")
                        .header("Origin", "http://google.pl")
                        .header("Access-Control-Request-Method", "OPTIONS")
                        .header("Access-Control-Request-Headers", "*"))
                .andReturn();

        //then
        assertEquals("Invalid CORS request", mvcResult1.getResponse().getContentAsString());
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult1.getResponse().getStatus());
    }

    @Test
    void testCorsConfigWithBadMethod() throws Exception{
        //when
        MvcResult mvcResult2 = mockMvc.perform(options("/available_codes")
                        .header("Origin", "http://google.com")
                        .header("Access-Control-Request-Method", "TRACE")
                        .header("Access-Control-Request-Headers", "*"))
                .andReturn();
        //then
        assertEquals("Invalid CORS request", mvcResult2.getResponse().getContentAsString());
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult2.getResponse().getStatus());
    }
}