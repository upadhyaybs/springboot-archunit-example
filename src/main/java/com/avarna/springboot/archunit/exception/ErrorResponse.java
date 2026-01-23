package com.tp.springboot.archunit.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 938636069700036657L;

	private UUID refId;

    private HttpStatus status;

    private int statusCode;

    private String errorMessage;
    private Exception exception;
    private Date timestamp= new Date();

    public ErrorResponse(UUID refId, String message,HttpStatus status){
        setRefId(refId);
        setErrorMessage(message);
        setStatus(status);
        setStatusCode(status.value());
    }
}
