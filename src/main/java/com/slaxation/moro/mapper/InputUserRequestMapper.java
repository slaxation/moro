package com.slaxation.moro.mapper;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.model.UserEntity;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface InputUserRequestMapper extends EntityDTOMapper<UserEntity, InputUserRequestDTO> {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "password", target = "password", qualifiedByName = "encodePassword")
    })
    UserEntity dtoToEntity(InputUserRequestDTO inputUserRequestDTO, @MappingTarget UserEntity userEntity);

    // Custom method for encoding passwords
    @Named("encodePassword")
    default String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
