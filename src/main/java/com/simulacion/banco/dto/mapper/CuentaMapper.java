package com.simulacion.banco.dto.mapper;

import com.simulacion.banco.dto.CuentaDto;
import com.simulacion.banco.entity.Cuenta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaDto toDto(Cuenta cuenta);
    Cuenta toEntity(CuentaDto cuentaDto);
}
