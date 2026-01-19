package com.tinnova.veiculos.business.repository;

import com.tinnova.veiculos.business.entity.Veiculo;
import com.tinnova.veiculos.web.dto.RelatorioMarcaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>,
        JpaSpecificationExecutor<Veiculo> {

    Optional<Veiculo> findByIdAndAtivoTrue(Long id);

    boolean existsByPlaca(String placa);

    @Query("""
        SELECT new com.tinnova.veiculos.web.dto.RelatorioMarcaDTO(v.marca, COUNT(v))
        FROM Veiculo v
        WHERE v.ativo = true
        GROUP BY v.marca
    """)
    List<RelatorioMarcaDTO> relatorioPorMarca();
}
