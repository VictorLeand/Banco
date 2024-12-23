package com.simulacion.banco.service.impl;

import com.simulacion.banco.entity.Cuenta;
import com.simulacion.banco.exception.ModelNotFoundException;
import com.simulacion.banco.repository.RepositoryCuenta;
import com.simulacion.banco.service.CuentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CuentaServiceImpl implements CuentaService {

    private final RepositoryCuenta repositoryCuenta;

    public CuentaServiceImpl(RepositoryCuenta repositoryCuenta) {
        this.repositoryCuenta = repositoryCuenta;
    }

    @Override
    public Cuenta consultarCuenta(Integer id) {
        return repositoryCuenta.findById(id).orElseThrow(() -> new ModelNotFoundException("Cuenta no encontrada con id " + id));
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        return repositoryCuenta.save(cuenta);
    }

    @Override
    public Cuenta actualizarCuenta(Cuenta modificarCuenta) {
        return repositoryCuenta.save(modificarCuenta);
    }

    @Override
    public void eliminar(Integer id) {
        Cuenta cuenta = this.consultarCuenta(id);
        if (cuenta != null){
            repositoryCuenta.deleteById(id);
        }

    }

    @Override
    public List<Cuenta> todasLasCuentas() {
        return repositoryCuenta.findAll();
    }
}
