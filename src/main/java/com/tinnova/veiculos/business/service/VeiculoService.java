package com.tinnova.veiculos.business.service;

import com.tinnova.veiculos.web.dto.RelatorioMarcaDTO;
import com.tinnova.veiculos.web.dto.VeiculoRequestDTO;
import com.tinnova.veiculos.web.dto.VeiculoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface VeiculoService {

    Page<VeiculoResponseDTO> listar(
            String marca,
            Integer ano,
            String cor,
            BigDecimal minPreco,
            BigDecimal maxPreco,
            Pageable pageable
    );

    VeiculoResponseDTO buscarPorId(Long id);

    VeiculoResponseDTO criar(VeiculoRequestDTO dto);

    VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto);

    VeiculoResponseDTO atualizarParcial(Long id, VeiculoRequestDTO dto);

    void remover(Long id);

    List<RelatorioMarcaDTO> relatorioPorMarca();
}

