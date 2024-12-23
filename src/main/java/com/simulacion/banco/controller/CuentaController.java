package com.simulacion.banco.controller;

import com.simulacion.banco.entity.Cliente;
import com.simulacion.banco.entity.Cuenta;
import com.simulacion.banco.repository.RepositoryCuenta;
import com.simulacion.banco.repository.RepositoryMovimiento;
import com.simulacion.banco.service.impl.ClienteServiceImpl;
import com.simulacion.banco.service.impl.CuentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private RepositoryCuenta repositoryCuenta;

    @Autowired
    private RepositoryMovimiento repositoryMovimiento;

    @Autowired
    private CuentaServiceImpl cuentaService;

    public CuentaController(ClienteServiceImpl clienteService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/consultar-cuenta/{id}")
    public ResponseEntity<Cuenta> consultarCuenta (@PathVariable Integer id){
        return ResponseEntity.ok().body(cuentaService.consultarCuenta(id));
    }

    @GetMapping("/todos-las-cuentas")
    public ResponseEntity<List<Cuenta>> consultarTodosLasCuentas (){
        return new ResponseEntity<>(cuentaService.todasLasCuentas(), HttpStatus.OK);
    }

    @PostMapping("/guardar-cuenta")
    public ResponseEntity<Cuenta> crearCuenta (@RequestBody Cuenta cuenta){
        return new ResponseEntity<>(cuentaService.crearCuenta(cuenta), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cuenta> actualizarCuenta(@RequestBody Cuenta modificarCuenta){
        return ResponseEntity.ok().body(cuentaService.actualizarCuenta(modificarCuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCuenta (@PathVariable Integer id){
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
