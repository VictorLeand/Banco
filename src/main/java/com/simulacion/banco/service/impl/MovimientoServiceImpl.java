package com.simulacion.banco.service.impl;

import com.simulacion.banco.dto.CuentaReporteDto;
import com.simulacion.banco.dto.MovimientoDto;
import com.simulacion.banco.dto.ReporteDto;
import com.simulacion.banco.dto.mapper.CuentaMapper;
import com.simulacion.banco.dto.mapper.MovimientoMapper;
import com.simulacion.banco.entity.Cliente;
import com.simulacion.banco.entity.Cuenta;
import com.simulacion.banco.entity.Movimiento;
import com.simulacion.banco.enums.TipoMovimiento;
import com.simulacion.banco.exception.AccountsEmptyException;
import com.simulacion.banco.exception.InsufficienteMoneyException;
import com.simulacion.banco.exception.InvalidDateException;

import com.simulacion.banco.exception.MovimientoEmptyException;
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

    private final CuentaMapper cuentaMapper;

    private final MovimientoMapper movimientoMapper;

    public MovimientoServiceImpl(RepositoryMovimiento repositoryMovimiento, ClienteServiceImpl clienteService, CuentaServiceImpl cuentaService, CuentaMapper cuentaMapper, MovimientoMapper movimientoMapper) {
        this.repositoryMovimiento = repositoryMovimiento;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.cuentaMapper = cuentaMapper;
        this.movimientoMapper = movimientoMapper;
    }

    private void validarMovimientos(MovimientoDto movimiento, Cuenta cuenta){

        if (movimiento == null || movimiento.getTipo() == null || movimiento.getValor() == null){
            throw new MovimientoEmptyException("El movimiento o su valor no pueden ser nulos");
        }

        if  (movimiento.getTipo() == TipoMovimiento.DEBITO &&
                movimiento.getValor().compareTo(cuenta.getSaldo()) > 0) {
            throw new InsufficienteMoneyException("Saldo insuficiente para realizar el movimiento");
        }

    }

    private void validarFechas (LocalDateTime fechaInicio, LocalDateTime fechaFinal, Cliente cliente){

        if (fechaInicio == null || fechaFinal == null || fechaInicio.isAfter(fechaFinal)) {
            throw new InvalidDateException("El rango de fechas no es válida");
        }

        if (cliente.getCuentas().isEmpty()) {
            throw new AccountsEmptyException("Este cliente no tiene cuentas asociadas");
        }
    }

    @Override
    public MovimientoDto registrarMovimiento(Integer cuentaId, MovimientoDto movimiento) {

        Cuenta cuenta = cuentaService.consultarCuenta(cuentaId);

        validarMovimientos(movimiento, cuenta);



        if (movimiento.getTipo() == TipoMovimiento.DEBITO) {

            cuenta.setSaldo(cuenta.getSaldo().subtract(movimiento.getValor()));
            log.info("Retiro exitoso por valor de = {}. El nuevo saldo de la cuenta es de = {}",
                   movimiento.getValor(), cuenta.getSaldo());

        } else if (movimiento.getTipo() == TipoMovimiento.CREDITO) {
            cuenta.setSaldo(cuenta.getSaldo().add(movimiento.getValor()));
            log.info("Consignación exitosa por valor de = {}. El nuevo saldo de la cuenta es de = {}",
                    movimiento.getValor(), cuenta.getSaldo());
        } else {
            throw new IllegalArgumentException("Debe elegir un movimiento");
        }

        cuentaService.actualizarCuenta(cuenta);
        movimiento.setCuenta(cuentaMapper.toDto(cuenta));
        return movimientoMapper.toDto( repositoryMovimiento.save(movimientoMapper.toEntity(movimiento)) );
    }

    @Override
    public BigDecimal reporteMovimientos(Integer cuentaId, TipoMovimiento tipo, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return repositoryMovimiento.reporteMovimientos(cuentaId, tipo, fechaInicio, fechaFinal);
    }

    public ReporteDto reporteCliente(Integer clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        Cliente cliente = clienteService.consultarCliente(clienteId);

       validarFechas(fechaInicio, fechaFinal, cliente);

        log.info("Generando reporte para el cliente: {} (Id: {})", cliente.getNombre(), clienteId);

        List<CuentaReporteDto> cuentas = cliente.getCuentas().stream().map(cuenta -> {
            BigDecimal totalDebitos = repositoryMovimiento.reporteMovimientos(
                    cuenta.getId(), TipoMovimiento.DEBITO, fechaInicio, fechaFinal);

            BigDecimal totalCreditos = repositoryMovimiento.reporteMovimientos(
                    cuenta.getId(), TipoMovimiento.CREDITO, fechaInicio, fechaFinal);

            log.info("Número de cuenta: {} | Total Débitos: {} | Total Créditos: {}",
                    cuenta.getNumero(), totalDebitos, totalCreditos);

            return new CuentaReporteDto(cuenta.getNumero(),
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


