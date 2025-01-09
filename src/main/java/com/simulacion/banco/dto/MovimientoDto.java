package com.simulacion.banco.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDto {

    public enum tipoMovimiento{
        CREDITO, DEBITO
    }

    private Integer id;
    private BigDecimal valor;
    private LocalDateTime fecha;
    private MovimientoDto.tipoMovimiento tipo;
    private CuentaDto cuenta;
}
