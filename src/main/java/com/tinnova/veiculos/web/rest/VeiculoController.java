package com.tinnova.veiculos.web.rest;

import com.tinnova.veiculos.business.service.VeiculoService;
import com.tinnova.veiculos.web.dto.RelatorioMarcaDTO;
import com.tinnova.veiculos.web.dto.VeiculoRequestDTO;
import com.tinnova.veiculos.web.dto.VeiculoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Page<VeiculoResponseDTO> listar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) BigDecimal minPreco,
            @RequestParam(required = false) BigDecimal maxPreco,
            Pageable pageable
    ) {
        return service.listar(marca, ano, cor, minPreco, maxPreco, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public VeiculoResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VeiculoResponseDTO> criar(
            @RequestBody @Valid VeiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public VeiculoResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid VeiculoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public VeiculoResponseDTO atualizarParcial(
            @PathVariable Long id,
            @RequestBody VeiculoRequestDTO dto) {
        return service.atualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorios/por-marca")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<RelatorioMarcaDTO> relatorio() {
        return service.relatorioPorMarca();
    }
}

