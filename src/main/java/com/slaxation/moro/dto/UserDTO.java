package com.slaxation.moro.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@JsonTypeName("user")
@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;

    private String name;

    private String username;

    private String password;

}
