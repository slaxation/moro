package com.slaxation.moro.mapper;

import org.mapstruct.MappingTarget;

import java.io.Serializable;

public interface EntityDTOMapper<ENTITY extends Serializable, DTO> {

    DTO entityToDto(ENTITY entity);

    ENTITY dtoToEntity(DTO dto);

    DTO entityToDto(ENTITY entity, @MappingTarget DTO target);

    ENTITY dtoToEntity(DTO dto, @MappingTarget ENTITY entity);
}
