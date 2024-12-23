package com.simulacion.banco.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDto {


    private String nombre;
    private String direccion;
    private String telefono;
    private List<CuentaReporteDto> cuentas;


}
