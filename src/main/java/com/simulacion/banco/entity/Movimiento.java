package com.simulacion.banco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movimiento")
public class Movimiento {

    public enum tipoMovimiento{
        CREDITO, DEBITO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private BigDecimal valor;
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    private tipoMovimiento tipo;

    //@JsonBackReference
    //@ManyToOne
    //@JoinColumn(name = "id_cliente", referencedColumnName = "id")
    //private Cliente cliente;

    @JsonBackReference // evita que el lado "no propietario" (el que depende del primero) se serialice,
    // evitando así que se produzca un ciclo infinito al tratar de serializar ambos lados de la relación.
    @ManyToOne
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    private Cuenta cuenta;
}
