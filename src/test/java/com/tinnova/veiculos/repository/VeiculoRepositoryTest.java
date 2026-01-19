package com.tinnova.veiculos.repository;

import com.tinnova.veiculos.business.entity.Veiculo;
import com.tinnova.veiculos.business.repository.VeiculoRepository;
import com.tinnova.veiculos.web.dto.RelatorioMarcaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class VeiculoRepositoryTest {

    @Autowired
    VeiculoRepository repository;

    @Test
    void deveRetornarRelatorioPorMarca() {

        Veiculo v1 = new Veiculo();
        v1.setMarca("Toyota");
        v1.setPrecoDolar(BigDecimal.ZERO);
        v1.setAtivo(true);

        Veiculo v2 = new Veiculo();
        v2.setMarca("Toyota");
        v2.setPrecoDolar(BigDecimal.ONE);
        v2.setAtivo(true);

        repository.saveAll(List.of(v1, v2));

        List<RelatorioMarcaDTO> relatorio = repository.relatorioPorMarca();

        assertEquals(1, relatorio.size());
        assertEquals(2L, relatorio.get(0).total());
    }
}

