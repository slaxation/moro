package com.slaxation.moro.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private String code;
    private String message;
    private String localizedMessage;
    private HttpStatus httpStatus;

    public BaseException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.httpStatus = status;
        this.localizedMessage = message;
    }

}
