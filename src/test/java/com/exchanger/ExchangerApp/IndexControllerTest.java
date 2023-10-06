package com.exchanger.ExchangerApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());
    }
    @Test
    void testGetLocalDates() throws Exception {
        mockMvc.perform(get("/daty"))
                .andExpect(status().isOk());
    }
    //excelResponse???
}
