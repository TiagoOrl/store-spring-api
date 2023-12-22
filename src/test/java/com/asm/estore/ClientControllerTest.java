package com.asm.estore;

import com.asm.estore.controller.ClientController;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientService service;
    private final String endpoint = "/api/client";

    @Test
    void shouldReturnCategory() throws Exception {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setEmail("tiago@mail.com");
        dto.setFirstName("Tiago");

        MvcResult response = mockMvc.perform(
                post(endpoint + "/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()).andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualTo("");
    }
}

