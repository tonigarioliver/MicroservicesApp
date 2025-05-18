package com.antonigari.user.service.service.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private final ServiceErrorCatalog errorCatalog;
    private final HttpStatus httpStatus;

    public ServiceException(final ServiceErrorCatalog errorCatalog, final HttpStatus httpStatus,
                            final String customMessage) {
        super(customMessage != null ? customMessage : errorCatalog.getMessage());
        this.errorCatalog = errorCatalog;
        this.httpStatus = httpStatus;
    }

    public ServiceErrorCatalog getErrorCatalog() {
        return this.errorCatalog;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}



