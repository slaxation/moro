package com.slaxation.moro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.slaxation.moro.constants.UserConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonTypeName("registerRequest")
@Builder
public class InputUserRequestDTO implements Serializable {

    @NotNull
    @Size(min = UserConstants.USERNAME_LENGTH_MIN,
            max = UserConstants.USERNAME_LENGTH_MAX)
    @JsonProperty("username")
    private String username;

    @NotNull
    @Size(min = UserConstants.PASSWORD_LENGTH_MIN)
    @JsonProperty("password")
    private String password;

    @Size(max = UserConstants.NAME_LENGTH_MAX)
    @JsonProperty("name")
    private String name;
}
