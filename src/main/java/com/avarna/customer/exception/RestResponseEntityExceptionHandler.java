package com.avarna.customer.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handleCustomerNotFound(CustomerNotFoundException exception){
        return buildErrorResponse(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    ErrorResponse buildErrorResponse(String message, HttpStatus status){
       return new ErrorResponse(UUID.randomUUID(),message, status);
    }

}
