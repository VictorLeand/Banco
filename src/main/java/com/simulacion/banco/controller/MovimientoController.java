package com.simulacion.banco.controller;

import com.simulacion.banco.dto.MovimientoDto;
import com.simulacion.banco.dto.ReporteDto;
import com.simulacion.banco.entity.Movimiento;
import com.simulacion.banco.service.impl.MovimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<ReporteDto> obtenerMovimientos (@RequestParam Integer cuentaId,
                                                          @RequestParam Movimiento.tipoMovimiento tipo,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFinal){
        return ResponseEntity.ok().body(movimientoService.reporteCliente(cuentaId, fechaInicio, fechaFinal));
    }

    @PostMapping("/guardar-movimiento/{id}")
    public ResponseEntity<MovimientoDto> registrarMovimiento (@PathVariable Integer id, @RequestBody MovimientoDto movimientoDto){
        return ResponseEntity.ok().body(movimientoService.registrarMovimiento(id, movimientoDto));
    }
}
