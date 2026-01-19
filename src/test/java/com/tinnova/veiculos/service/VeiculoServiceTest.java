package com.tinnova.veiculos.service;

import com.tinnova.veiculos.business.entity.Veiculo;
import com.tinnova.veiculos.business.exception.BusinessException;
import com.tinnova.veiculos.business.repository.VeiculoRepository;
import com.tinnova.veiculos.business.service.impl.VeiculoServiceImpl;
import com.tinnova.veiculos.integration.DollarExchangeService;
import com.tinnova.veiculos.web.dto.VeiculoRequestDTO;
import com.tinnova.veiculos.web.dto.VeiculoResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceImplTest {

    @Mock
    VeiculoRepository repository;

    @Mock
    DollarExchangeService dollarService;

    @InjectMocks
    VeiculoServiceImpl service;

    @Test
    void deveCriarVeiculo() {
        when(repository.existsByPlaca("ABC1D23")).thenReturn(false);
        when(dollarService.getDollarRate()).thenReturn(BigDecimal.valueOf(5));

        VeiculoRequestDTO dto = new VeiculoRequestDTO(
                "Toyota","Corolla","Prata",2022,"ABC1D23",BigDecimal.valueOf(100000)
        );

        Veiculo salvo = new Veiculo();
        salvo.setId(1L);
        salvo.setPrecoDolar(BigDecimal.valueOf(20000));

        when(repository.save(any())).thenReturn(salvo);

        VeiculoResponseDTO resp = service.criar(dto);

        assertNotNull(resp);
        assertEquals(1L, resp.id());
    }

    @Test
    void deveFalharQuandoPlacaDuplicada() {
        when(repository.existsByPlaca(any())).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> service.criar(mock(VeiculoRequestDTO.class)));
    }

    @Test
    void deveAplicarSoftDelete() {
        Veiculo v = new Veiculo();
        v.setId(1L);
        v.setAtivo(true);

        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(v));

        service.remover(1L);

        assertFalse(v.getAtivo());
    }
}


