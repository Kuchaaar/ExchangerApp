package com.exchanger.ExchangerApp.config;

import com.exchanger.currency.config.security.cors.CorsConfig;
import com.exchanger.currency.config.security.cors.CorsProperties;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.web.CurrencyController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CurrencyController.class, CorsConfig.class,
        CorsProperties.class,CurrencyService.class})
@AutoConfigureMockMvc
class PaginationTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CurrencyRepository currencyRepository;
    @Test
    void paginationTest() throws Exception{
        //when
        mockMvc.perform(get("/available_codes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        //then
        verify(currencyRepository).availableCodes(pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
    }
}
