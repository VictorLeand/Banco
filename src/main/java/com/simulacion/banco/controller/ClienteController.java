package com.simulacion.banco.controller;

import com.simulacion.banco.entity.Cliente;
import com.simulacion.banco.service.impl.ClienteServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {


    @Autowired
    private ClienteServiceImpl clienteService;

    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/consultar-cliente/{id}")
    public ResponseEntity<Cliente> consultarCliente (@PathVariable Integer id){
        return ResponseEntity.ok().body(clienteService.consultarCliente(id));
    }

    @GetMapping("/todos-los-clientes")
    public ResponseEntity<List<Cliente>> consultarTodosLosCliente (){
        return new ResponseEntity<>(clienteService.todosLosClientes(), HttpStatus.OK);
    }

    @PostMapping("/guardar-clientes")
    public ResponseEntity<Cliente> crearCliente (@RequestBody Cliente cliente){
        return new ResponseEntity<>(clienteService.crearCliente(cliente), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente modificarCliente){
        return ResponseEntity.ok().body(clienteService.actualizarCliente(modificarCliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCLiente (@PathVariable Integer id){
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generar-reporte")
    public ResponseEntity<Void> generarreporte(HttpServletResponse response) throws IOException {

        response.setContentType("application/xls");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "reporte.xls");

        clienteService.generarreporte(response);
        return ResponseEntity.noContent().build();
    }

}
