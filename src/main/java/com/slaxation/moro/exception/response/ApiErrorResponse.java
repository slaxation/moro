package com.slaxation.moro.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    private String code;
    private String message;

}
