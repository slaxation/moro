package com.slaxation.moro.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slaxation.moro.constants.UserConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(UserConstants.USERNAME_LENGTH_MIN)
    @Max(UserConstants.USERNAME_LENGTH_MAX)
    private String username;

    @NotNull
    @Min(UserConstants.PASSWORD_LENGTH_MIN)
    private String password;

    @Max(UserConstants.NAME_LENGTH_MAX)
    @JsonProperty("name")
    private String name;
}
