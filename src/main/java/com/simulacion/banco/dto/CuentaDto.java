package com.simulacion.banco.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaDto {

    private Integer id;
    private Long numero;
    private BigDecimal saldo;

}
