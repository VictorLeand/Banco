package com.simulacion.banco.service;

import com.simulacion.banco.dto.ReporteDto;
import com.simulacion.banco.entity.Movimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MovimientoService {

    Movimiento registrarMovimiento(Integer cuentaId, Movimiento movimiento);

    BigDecimal reporteMovimientos(Integer cuentaId, Movimiento.tipoMovimiento tipo,
                                           LocalDateTime fechaInicio, LocalDateTime fechaFin);

    ReporteDto reporteCliente(Integer clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFinal);

}