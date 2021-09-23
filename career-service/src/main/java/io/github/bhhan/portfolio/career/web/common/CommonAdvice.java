package io.github.bhhan.portfolio.career.web.common;

import io.github.bhhan.portfolio.common.error.ErrorCode;
import io.github.bhhan.portfolio.common.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException e){
        return new ErrorResponse(ErrorCode.BAD_REQUEST, e.getFieldErrors());
    }
}
