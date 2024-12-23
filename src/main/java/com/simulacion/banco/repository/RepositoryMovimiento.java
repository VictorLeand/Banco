package com.simulacion.banco.repository;

import com.simulacion.banco.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RepositoryMovimiento extends JpaRepository<Movimiento, Integer> {


    @Query("SELECT SUM(m.valor) FROM Movimiento m WHERE m.cuenta.id = :cuentaId AND m.tipo = :tipo AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal reporteMovimientos(@Param("cuentaId") Integer cuentaId,
                                  @Param("tipo") Movimiento.tipoMovimiento tipo,
                                  @Param("fechaInicio") LocalDateTime fechaInicio,
                                  @Param("fechaFin") LocalDateTime fechaFinal);
}
