package com.tinnova.veiculos.business.service.impl;

import com.tinnova.veiculos.business.entity.Veiculo;
import com.tinnova.veiculos.business.exception.BusinessException;
import com.tinnova.veiculos.business.exception.ResourceNotFoundException;
import com.tinnova.veiculos.business.repository.VeiculoRepository;
import com.tinnova.veiculos.business.service.VeiculoService;
import com.tinnova.veiculos.business.specification.VeiculoSpecification;
import com.tinnova.veiculos.integration.DollarExchangeService;
import com.tinnova.veiculos.web.dto.RelatorioMarcaDTO;
import com.tinnova.veiculos.web.dto.VeiculoRequestDTO;
import com.tinnova.veiculos.web.dto.VeiculoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository repository;
    private final DollarExchangeService dollarService;

    public VeiculoServiceImpl(VeiculoRepository repository,
                              DollarExchangeService dollarService) {
        this.repository = repository;
        this.dollarService = dollarService;
    }

    @Override
    public Page<VeiculoResponseDTO> listar(
            String marca, Integer ano, String cor,
            BigDecimal minPreco, BigDecimal maxPreco,
            Pageable pageable) {

        var spec = VeiculoSpecification.filtro(
                marca, ano, cor, minPreco, maxPreco
        );

        return repository.findAll(spec, pageable)
                .map(this::toResponse);
    }

    @Override
    public VeiculoResponseDTO buscarPorId(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
    }

    @Override
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {

        if (repository.existsByPlaca(dto.placa()))
            throw new BusinessException("Placa já cadastrada");

        Veiculo v = new Veiculo();
        copiarBase(dto, v);

        BigDecimal dolar = dollarService.getDollarRate();
        v.setPrecoDolar(dto.precoEmReais().divide(dolar, 2, BigDecimal.ROUND_HALF_UP));

        return toResponse(repository.save(v));
    }

    @Override
    public VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto) {
        Veiculo v = buscarEntidade(id);
        copiarBase(dto, v);
        atualizarPreco(dto.precoEmReais(), v);
        return toResponse(repository.save(v));
    }

    @Override
    public VeiculoResponseDTO atualizarParcial(Long id, VeiculoRequestDTO dto) {
        Veiculo v = buscarEntidade(id);

        if (dto.marca() != null) v.setMarca(dto.marca());
        if (dto.modelo() != null) v.setModelo(dto.modelo());
        if (dto.cor() != null) v.setCor(dto.cor());
        if (dto.ano() != null) v.setAno(dto.ano());
        if (dto.precoEmReais() != null)
            atualizarPreco(dto.precoEmReais(), v);

        return toResponse(repository.save(v));
    }

    @Override
    public void remover(Long id) {
        Veiculo v = buscarEntidade(id);
        v.setAtivo(false);
        repository.save(v);
    }

    @Override
    public List<RelatorioMarcaDTO> relatorioPorMarca() {
        return repository.relatorioPorMarca();
    }

    // ===== Métodos privados (Clean Code) =====

    private Veiculo buscarEntidade(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
    }

    private void copiarBase(VeiculoRequestDTO dto, Veiculo v) {
        v.setMarca(dto.marca());
        v.setModelo(dto.modelo());
        v.setCor(dto.cor());
        v.setAno(dto.ano());
        v.setPlaca(dto.placa());
    }

    private void atualizarPreco(BigDecimal precoReais, Veiculo v) {
        BigDecimal dolar = dollarService.getDollarRate();
        v.setPrecoDolar(precoReais.divide(dolar, 2, BigDecimal.ROUND_HALF_UP));
    }

    private VeiculoResponseDTO toResponse(Veiculo v) {
        return new VeiculoResponseDTO(
                v.getId(), v.getMarca(), v.getModelo(),
                v.getCor(), v.getAno(), v.getPlaca(), v.getPrecoDolar()
        );
    }
}

