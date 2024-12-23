package com.simulacion.banco.service.impl;

import com.simulacion.banco.entity.Cliente;
import com.simulacion.banco.exception.ModelNotFoundException;
import com.simulacion.banco.repository.RepositoryCliente;
import com.simulacion.banco.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ClienteServiceImpl implements ClienteService {

    private final RepositoryCliente clienteRepository;

    public ClienteServiceImpl(RepositoryCliente clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente consultarCliente(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Estudiante no encontrado con id " + id));
    }

    @Override
    public Cliente actualizarCliente(Cliente modificarCliente) {
        return clienteRepository.save(modificarCliente);
    }

    @Override
    public void eliminar(Integer id) {
        Cliente cliente = this.consultarCliente(id);
        if (cliente != null){
            clienteRepository.deleteById(id);
        }

    }

    @Override
    public List<Cliente> todosLosClientes() {
        return clienteRepository.findAll();
    }
}
