package com.tinnova.veiculos.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tinnova.veiculos.web.dto.LoginRequest;
import com.tinnova.veiculos.web.dto.VeiculoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class VeiculoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    String token;

    @BeforeEach
    void setup() throws Exception {

        LoginRequest login = new LoginRequest("admin", "admin123");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        token = mapper.readTree(response).get("token").asText();
    }

    @Test
    void fluxoCompleto() throws Exception {

        VeiculoRequestDTO dto = new VeiculoRequestDTO(
                "Toyota","Corolla","Prata",2022,"ZZZ1234",
                BigDecimal.valueOf(100000)
        );

        mockMvc.perform(post("/veiculos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/veiculos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
