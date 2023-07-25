package com.tp.springboot.archunit.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handleCustomerNotFound(CustomerNotFoundException exception){
       // ErrorResponse response=buildErrorResponse(exception.getMessage(),HttpStatus.NOT_FOUND);
        return buildErrorResponse(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

    ErrorResponse buildErrorResponse(String message, HttpStatus status){
       return new ErrorResponse(UUID.randomUUID(),message, status);
    }

}
