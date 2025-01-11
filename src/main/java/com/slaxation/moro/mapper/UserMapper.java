package com.slaxation.moro.mapper;

import com.slaxation.moro.dto.UserDTO;
import com.slaxation.moro.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityDTOMapper<UserEntity, UserDTO>{
}
