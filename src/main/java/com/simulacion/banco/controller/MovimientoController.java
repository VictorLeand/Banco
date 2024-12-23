package com.simulacion.banco.controller;

import com.simulacion.banco.dto.ReporteDto;
import com.simulacion.banco.entity.Movimiento;
import com.simulacion.banco.service.impl.MovimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/movimiento")
public class MovimientoController {

    @Autowired
    private MovimientoServiceImpl movimientoService;


    public MovimientoController(MovimientoServiceImpl movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/obtener-movimientos")
    public ResponseEntity<ReporteDto> obtenerMovimientos (Integer cuentaId, Movimiento.tipoMovimiento tipo, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        return ResponseEntity.ok().body(movimientoService.reporteCliente(cuentaId, fechaInicio, fechaFinal));
    }

    @PostMapping("/guardar-movimiento")
    public ResponseEntity<Movimiento> registrarMovimiento (@RequestParam Integer Id, @RequestBody Movimiento movimiento){
        return ResponseEntity.ok().body(movimientoService.registrarMovimiento(Id, movimiento));
    }
}
