package com.simulacion.banco.dto.mapper;

import com.simulacion.banco.dto.MovimientoDto;
import com.simulacion.banco.entity.Movimiento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    MovimientoDto toDto(Movimiento movimiento);
    Movimiento toEntity(MovimientoDto movimientoDto);
}
