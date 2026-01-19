package com.tinnova.veiculos.service;

import com.tinnova.veiculos.business.exception.BusinessException;
import com.tinnova.veiculos.business.repository.VeiculoRepository;
import com.tinnova.veiculos.business.service.impl.VeiculoServiceImpl;
import com.tinnova.veiculos.integration.DollarExchangeService;
import com.tinnova.veiculos.web.dto.VeiculoRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    VeiculoRepository repository;

    @Mock
    DollarExchangeService dollarService;

    @InjectMocks
    VeiculoServiceImpl service;

    @Test
    void deveFalharQuandoPlacaDuplicada() {
        when(repository.existsByPlaca("ABC1234")).thenReturn(true);

        var dto = new VeiculoRequestDTO(
                "Ford","Ka","Preto",2020,"ABC1234", BigDecimal.TEN
        );

        assertThrows(BusinessException.class, () -> service.criar(dto));
    }
}

