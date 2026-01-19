package com.tinnova.veiculos.controller;

import com.tinnova.veiculos.business.service.VeiculoService;
import com.tinnova.veiculos.config.security.JwtTokenProvider;
import com.tinnova.veiculos.config.security.SecurityConfig;
import com.tinnova.veiculos.web.rest.VeiculoController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VeiculoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(VeiculoControllerTest.TestConfig.class)
class VeiculoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VeiculoService service;

    @Test
    void devePermitirGetSemToken() throws Exception {
        when(service.listar(any(), any(), any(), any(), any(), any()))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void devePermitirGetParaUser() throws Exception {
        when(service.listar(any(), any(), any(), any(), any(), any()))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveNegarPostParaUser() throws Exception {
        mockMvc.perform(post("/veiculos"))
                .andExpect(status().isForbidden());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        VeiculoService veiculoService() {
            return Mockito.mock(VeiculoService.class);
        }
    }
}




