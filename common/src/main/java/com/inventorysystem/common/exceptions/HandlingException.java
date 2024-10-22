package com.inventorysystem.common.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/* Exception handling */
@RestControllerAdvice
public class HandlingException {

    static Logger logger = LoggerFactory.getLogger(HandlingException.class);

    @ExceptionHandler
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> exception = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            exception.put(field, defaultMessage);
        });
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> invalidInputException(HttpMessageNotReadableException ex) {
        Map<String, String> exception = new HashMap<>();
        String field = "message";
        String message = "invalid input";
        exception.put(field, message);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
