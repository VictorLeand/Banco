package com.simulacion.banco.dto;

import com.simulacion.banco.enums.TipoMovimiento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDto {

    private Integer id;
    private BigDecimal valor;
    private LocalDateTime fecha;
    private TipoMovimiento tipo;
    private CuentaDto cuenta;
}
