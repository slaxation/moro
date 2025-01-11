package com.slaxation.moro.mapper;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InputUserRequestMapper extends EntityDTOMapper<InputUserRequestDTO, UserEntity>{
}
