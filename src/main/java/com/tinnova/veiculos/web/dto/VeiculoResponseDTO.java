package com.tinnova.veiculos.web.dto;

import java.math.BigDecimal;

public record VeiculoResponseDTO(
        Long id,
        String marca,
        String modelo,
        String cor,
        Integer ano,
        String placa,
        BigDecimal precoDolar
) {}
