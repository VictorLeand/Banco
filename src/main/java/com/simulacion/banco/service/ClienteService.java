package com.simulacion.banco.service;

import com.simulacion.banco.entity.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente crearCliente (Cliente cliente);
    Cliente consultarCliente (Integer id);
    Cliente actualizarCliente (Cliente modificarCliente);
    void eliminar(Integer id);
    List<Cliente> todosLosClientes();
}
