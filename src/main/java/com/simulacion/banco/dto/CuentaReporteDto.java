package com.simulacion.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaReporteDto {


    private Long numero;
    private BigDecimal saldoActual;
    private BigDecimal totalDebitos;
    private BigDecimal totalCreditos;


}
