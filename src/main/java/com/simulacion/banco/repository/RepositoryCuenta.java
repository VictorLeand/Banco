package com.simulacion.banco.repository;

import com.simulacion.banco.entity.Cliente;
import com.simulacion.banco.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryCuenta extends JpaRepository<Cuenta, Integer> {


}
