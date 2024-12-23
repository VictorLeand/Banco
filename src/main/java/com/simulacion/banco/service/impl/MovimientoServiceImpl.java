package com.simulacion.banco.service.impl;

import com.simulacion.banco.dto.CuentaReporteDto;
import com.simulacion.banco.dto.ReporteDto;
import com.simulacion.banco.entity.Cliente;
import com.simulacion.banco.entity.Cuenta;
import com.simulacion.banco.entity.Movimiento;
import com.simulacion.banco.exception.AccountsEmptyException;
import com.simulacion.banco.exception.InsufficienteMoneyException;
import com.simulacion.banco.exception.InvalidDateException;

import com.simulacion.banco.repository.RepositoryMovimiento;
import com.simulacion.banco.service.MovimientoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final RepositoryMovimiento repositoryMovimiento;

    private final CuentaServiceImpl cuentaService;

    private final ClienteServiceImpl clienteService;

    public MovimientoServiceImpl(RepositoryMovimiento repositoryMovimiento, ClienteServiceImpl clienteService, CuentaServiceImpl cuentaService) {
        this.repositoryMovimiento = repositoryMovimiento;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
    }

    @Override
    public Movimiento registrarMovimiento(Integer Id, Movimiento movimiento) {
        if (movimiento == null || movimiento.getTipo() == null || movimiento.getValor() == null){
            throw new IllegalArgumentException("El movimiento o su valor no pueden ser nulos");
        }
        Cuenta cuenta = cuentaService.consultarCuenta(Id);
        if (movimiento.getTipo() == Movimiento.tipoMovimiento.DEBITO) {
            if ((movimiento.getValor()).compareTo(cuenta.getSaldo()) > 0) {
                throw new InsufficienteMoneyException("Saldo insuficiente para realizar el movimiento");
            }
            cuenta.setSaldo(cuenta.getSaldo().subtract(movimiento.getValor()));
            log.info("Retiro exitoso por valor de = {}. El nuevo saldo de la cuenta es de = {}",
                   movimiento.getValor(), cuenta.getSaldo());

        } else if (movimiento.getTipo() == Movimiento.tipoMovimiento.CREDITO) {
            cuenta.setSaldo(cuenta.getSaldo().add(movimiento.getValor()));
            log.info("Consignación exitosa por valor de = {}. El nuevo saldo de la cuenta es de = {}",
                    movimiento.getValor(), cuenta.getSaldo());
        } else {
            throw new IllegalArgumentException("Debe elegir un movimiento");
        }
        cuentaService.actualizarCuenta(cuenta);
        movimiento.setCuenta(cuenta);
        return repositoryMovimiento.save(movimiento);
    }

    @Override
    public BigDecimal reporteMovimientos(Integer cuentaId, Movimiento.tipoMovimiento tipo, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return repositoryMovimiento.reporteMovimientos(cuentaId, tipo, fechaInicio, fechaFinal);
    }

    public ReporteDto reporteCliente(Integer clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        if (fechaInicio == null || fechaFinal == null || fechaInicio.isAfter(fechaFinal)){
            throw new InvalidDateException("El rango de fechas no es válida");
        }

        Cliente cliente = clienteService.consultarCliente(clienteId);
        if (cliente.getCuentas().isEmpty()){
            throw new AccountsEmptyException("Este cliente no tiene cuentas asociadas");
        }

        log.info("Generando reporte para el cliente: {} (Id: {})", cliente.getNombre(), clienteId);

        List<CuentaReporteDto> cuentas = cliente.getCuentas().stream().map(cuenta -> {
            BigDecimal totalDebitos = repositoryMovimiento.reporteMovimientos(
                    cuenta.getId(), Movimiento.tipoMovimiento.DEBITO, fechaInicio, fechaFinal);

            BigDecimal totalCreditos = repositoryMovimiento.reporteMovimientos(
                    cuenta.getId(), Movimiento.tipoMovimiento.CREDITO, fechaInicio, fechaFinal);

            log.info("Número de cuenta: {} | Total Débitos: {} | Total Créditos: {}",
                    cuenta.getNumero(), totalDebitos, totalCreditos);

            return new CuentaReporteDto (cuenta.getNumero(),
                    cuenta.getSaldo(),
                    totalDebitos != null ? totalDebitos : BigDecimal.ZERO,
                    totalCreditos != null ? totalCreditos : BigDecimal.ZERO
            );
        }).collect(Collectors.toList());

        return new ReporteDto(
                cliente.getNombre(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cuentas);
    }
}


