package com.antonigari.userservice.service.exception;

import org.springframework.http.HttpStatus;

public enum ServiceErrorCatalog {
    NOT_FOUND("", HttpStatus.NOT_FOUND),
    CONFLICT("", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    ServiceErrorCatalog(final String message, final HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public ServiceException exception(final String customMessage) {
        return new ServiceException(this, this.httpStatus, customMessage);
    }
}

