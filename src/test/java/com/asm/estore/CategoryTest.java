package com.asm.estore;

import com.asm.estore.controller.CategoryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.asm.estore.static_mocks.CategoryMocks.expectedAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryController controller;
    private final String endpoint = "/api/category";

    @Test
    void shouldReturnCategory() throws Exception {
        mockMvc.perform(get(endpoint)).andExpect(status().isOk())
                .andExpect(content().json(expectedAll));
    }
}
