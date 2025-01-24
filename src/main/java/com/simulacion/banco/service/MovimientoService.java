package com.simulacion.banco.service;

import com.simulacion.banco.dto.MovimientoDto;
import com.simulacion.banco.dto.ReporteDto;
import com.simulacion.banco.entity.Cuenta;
import com.simulacion.banco.entity.Movimiento;
import com.simulacion.banco.enums.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MovimientoService {

    MovimientoDto registrarMovimiento(Integer cuentaId, MovimientoDto movimiento);

    BigDecimal reporteMovimientos(Integer cuentaId, TipoMovimiento tipo,
                                           LocalDateTime fechaInicio, LocalDateTime fechaFin);

    ReporteDto reporteCliente(Integer clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFinal);

}
