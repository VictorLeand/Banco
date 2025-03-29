package com.simulacion.banco.dto;

import com.simulacion.banco.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePaginacionDto {

    private List<Cliente> lista;
    private Integer totalPaginas;
    private Integer totalElementos;
    private Integer numeroPagina;
    private Integer tamanioPagina;
}
