package com.exchanger.ExchangerApp.config;

import com.exchanger.currency.config.CorsConfig;
import com.exchanger.currency.config.CorsProperties;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.domain.currency.CurrencyService;
import com.exchanger.currency.web.CurrencyController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.exchanger.ExchangerApp.config.AbstractSpringDocTest.getContent;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {CurrencyController.class, CorsConfig.class, CorsProperties.class,
                CurrencyService.class, CurrencyRepository.class},
        properties = "repository.currency=memory")
@AutoConfigureMockMvc
class PaginationTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void emptyAvailableDatesPaginationTestData() throws Exception{
        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/available_dates"))
                .andExpect(status().isOk())
                .andExpect(content().json(getContent("config/emptyAvailableDatesPaginationTestData.json"), true));
    }
    @Test
    void emptyAvailableCodesPaginationTestData() throws Exception{
        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/available_codes"))
                .andExpect(status().isOk())
                .andExpect(content().json(getContent("config/emptyAvailableCodesPaginationTestData.json"), true));
    }
}