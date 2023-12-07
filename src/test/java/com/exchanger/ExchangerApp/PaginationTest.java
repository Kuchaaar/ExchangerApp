package com.exchanger.ExchangerApp;

import com.exchanger.currency.config.CorsConfig;
import com.exchanger.currency.config.CorsProperties;
import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.peristence.currency.InMemoryCurrencyRepository;
import com.exchanger.currency.web.CurrencyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

@WebMvcTest({CurrencyController.class, CorsConfig.class,
        CorsProperties.class})

class PaginationTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InMemoryCurrencyRepository inMemoryCurrencyRepository;


    @Test
    void paginationTest() throws Exception{
        //when
        MvcResult mvcResult = mockMvc.perform(options("/available_codes")
                        .header("Origin", "http://google.com")
                        .header("Access-Control-Request-Method", "OPTIONS")
                        .header("Access-Control-Request-Headers", "*"))
                .andReturn();
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(inMemoryCurrencyRepository).availableCodes(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();
        //then
        assertEquals(5,pageable.getPageSize());
    }

}
