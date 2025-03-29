package com.simulacion.banco.dto;

import com.simulacion.banco.entity.Cliente;
import lombok.Data;

import java.util.List;

@Data
public class ClientePaginacionDto {

    private List<Cliente> lista;
    private Integer totalPaginas;
    private Integer totalElementos;
    private Integer numeroPagina;
    private Integer tamanioPagian;
}
