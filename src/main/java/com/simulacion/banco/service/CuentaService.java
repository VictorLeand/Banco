package com.simulacion.banco.service;

import com.simulacion.banco.entity.Cuenta;

import java.util.List;

public interface CuentaService {

    Cuenta consultarCuenta (Integer id);
    Cuenta crearCuenta (Cuenta cuenta);
    Cuenta actualizarCuenta (Cuenta modificarCuenta);
    void eliminar (Integer id);
    List<Cuenta> todasLasCuentas();
}
