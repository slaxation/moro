package com.slaxation.moro.mapper;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.mapper.service.PasswordEncoderService;
import com.slaxation.moro.model.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InputUserRequestMapper extends EntityDTOMapper<UserEntity, InputUserRequestDTO> {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "password", target = "password", qualifiedByName = "encodePassword")
    })
    UserEntity dtoToEntity(InputUserRequestDTO inputUserRequestDTO, @MappingTarget UserEntity userEntity);

    // Custom method for encoding passwords
    @Named("encodePassword")
    default String encodePassword(String password) {
        PasswordEncoderService passwordEncoderService = new PasswordEncoderService();
        return passwordEncoderService.encodePassword(password);
    }
}
