package com.antonigari.iotdeviceservice.service.exception;

import org.springframework.http.HttpStatus;
public enum ServiceErrorCatalog {
    NOT_FOUND("", HttpStatus.NOT_FOUND),
    CONFLICT("", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    ServiceErrorCatalog(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ServiceException exception(String customMessage) {
        return new ServiceException(this, httpStatus, customMessage);
    }
}

