package org.bankmasr.irrigation.controllers;

import org.bankmasr.irrigation.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.internalServerError()
                .body(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
