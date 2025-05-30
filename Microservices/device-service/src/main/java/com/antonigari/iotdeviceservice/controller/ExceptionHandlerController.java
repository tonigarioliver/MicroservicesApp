package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.service.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(final ServiceException ex) {
        return new ResponseEntity<>(ex.getErrorCatalog().getMessage(), ex.getHttpStatus());
    }
}
