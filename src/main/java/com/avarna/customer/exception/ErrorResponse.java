package com.avarna.customer.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 938636069700036657L;

	private UUID refId;

    private HttpStatus status;

    private int statusCode;

    private String errorMessage;

    private Exception exception;

    private Date timestamp;

    public ErrorResponse(UUID refId, String message, HttpStatus status) {
        this.refId = refId;
        this.errorMessage = message;
        this.status = status;
        this.statusCode = status.value();
        this.timestamp = new Date();
    }
}
