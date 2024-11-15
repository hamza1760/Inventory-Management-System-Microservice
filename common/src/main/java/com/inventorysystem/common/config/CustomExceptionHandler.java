package com.inventorysystem.common.config;

import static com.inventorysystem.common.utilities.Constants.SOMETHING_WENT_WRONG;

import com.inventorysystem.common.customexception.BusinessException;
import com.inventorysystem.common.model.response.ExceptionResponse;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Summary :: For custom error handling.This method will catch any type of Java exceptions that get thrown.
     *
     * @param ex exception
     * @return ResponseEntity Object
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Set<String> details = new HashSet<>();
        if (ex.getCause() != null) {
            details.add(ex.getCause().getMessage());
        } else {
            details.add(ex.getLocalizedMessage());
        }
        log.error("Handle All Exception :" + ex.getLocalizedMessage());
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().status(false).message(SOMETHING_WENT_WRONG).details(details).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }


    /**
     * handleEmployeeExceptions.
     *
     * @param ex BusinessException
     * @return ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<Object> handleEmployeeExceptions(BusinessException ex) {
        Set<String> details = new HashSet<>();
        if (ex.getCause() != null) {
            details.add(ex.getCause().getMessage());
        } else {
            details.add(ex.getLocalizedMessage());
        }
        log.error("Business Exception :" + ex.getLocalizedMessage());
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().status(false).message(SOMETHING_WENT_WRONG).build();
        if (ex.getCustomObject() != null) {
            exceptionResponse.setDetails(ex.getCustomObject());
        } else {
            exceptionResponse.setDetails(details);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

}
