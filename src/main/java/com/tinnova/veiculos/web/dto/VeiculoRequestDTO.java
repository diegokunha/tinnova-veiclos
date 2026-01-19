package com.tinnova.veiculos.web.dto;

import java.math.BigDecimal;

public record VeiculoRequestDTO(
        String marca,
        String modelo,
        String cor,
        Integer ano,
        String placa,
        BigDecimal precoEmReais
) {}
