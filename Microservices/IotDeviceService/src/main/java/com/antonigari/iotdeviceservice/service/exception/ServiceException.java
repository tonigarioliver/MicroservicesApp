package com.antonigari.iotdeviceservice.service.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private final ServiceErrorCatalog errorCatalog;
    private final HttpStatus httpStatus;

    public ServiceException(ServiceErrorCatalog errorCatalog, HttpStatus httpStatus, String customMessage) {
        super(customMessage != null ? customMessage : errorCatalog.getMessage());
        this.errorCatalog = errorCatalog;
        this.httpStatus = httpStatus;
    }

    public ServiceErrorCatalog getErrorCatalog() {
        return errorCatalog;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}



