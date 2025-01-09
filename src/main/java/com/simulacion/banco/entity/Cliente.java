package com.simulacion.banco.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;

    @JsonManagedReference // permite que el lado "propietario" de la relación se serialice normalmente.
    @OneToMany (mappedBy = "cliente")
    private List<Cuenta> cuentas = new ArrayList<>();
}
