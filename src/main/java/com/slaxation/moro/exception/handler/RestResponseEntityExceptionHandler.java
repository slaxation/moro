package com.slaxation.moro.exception.handler;

import com.slaxation.moro.exception.BaseException;
import com.slaxation.moro.exception.NotFoundException;
import com.slaxation.moro.exception.response.ApiErrorResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, UsernameNotFoundException.class})
    private ResponseEntity<ApiErrorResponse> handleApplicationException(BaseException exception) {
        ApiErrorResponse apiErrorResponse = getApiErrorResponse(exception);
        return new ResponseEntity<>(apiErrorResponse, exception.getHttpStatus());
    }

    protected ApiErrorResponse getApiErrorResponse(BaseException exception) {
        ApiErrorResponse r = new ApiErrorResponse();
        r.setCode(exception.getCode());
        r.setMessage(StringUtils.isNotBlank(exception.getLocalizedMessage()) ? exception.getLocalizedMessage() : exception.getMessage());

        return r;
    }
}
