package com.asm.estore;

import com.asm.estore.controller.ClientController;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientService service;
    private final String endpoint = "/api/client";

    /**
     * should return 200 on valid body request DTO
     * @throws Exception
     */
    @Test
    void shouldReturnStatusOKOnCreateClient() throws Exception {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setFirstName("Tiago");
        dto.setSecondName("orlando de jesus");
        dto.setCountryId("123564737652");
        dto.setEmail("tiago@mail.com");
        dto.setDob("1996-04-05");

        mockMvc.perform(
                post(endpoint + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    /**
     * should return 400 on missing first name
     * @throws Exception
     */
    @Test
    void shouldReturnBadRequestOnInvalidRequest() throws Exception {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setEmail("tiago@mail.com");
//        dto.setFirstName("Tiago");
        dto.setSecondName("orlando de jesus");
        dto.setCountryId("123564737652");
        dto.setDob("1996-04-05");

        mockMvc.perform(
                        post(endpoint + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Should return 400 on invalid email
     * @throws Exception
     */
    @Test
    void shouldReturnBadRequestOnInvalidEmail() throws Exception {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setFirstName("Tiago");
        dto.setSecondName("orlando de jesus");
        dto.setCountryId("123564737652");
        dto.setEmail("tiago@@mail.com");
        dto.setDob("1996-04-05");

        mockMvc.perform(
                        post(endpoint + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Should return 400 on invalid date of birth (dob)
     * @throws Exception
     */
    @Test
    void shouldReturnBadRequestOnInvalidDob() throws Exception {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setFirstName("Tiago");
        dto.setSecondName("orlando de jesus");
        dto.setCountryId("123564737652");
        dto.setEmail("tiago@@mail.com");
        dto.setDob("196-04-05");

        mockMvc.perform(
                        post(endpoint + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}

