package io.github.bhhan.portfolio.oauth2.web;

import io.github.bhhan.portfolio.common.error.ErrorCode;
import io.github.bhhan.portfolio.common.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return new ErrorResponse(ErrorCode.BAD_REQUEST, e.getBindingResult().getFieldErrors());
    }
}
