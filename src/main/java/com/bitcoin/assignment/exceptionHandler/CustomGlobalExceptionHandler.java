package com.bitcoin.assignment.exceptionHandler;

import com.bitcoin.assignment.exception.APIError;
import com.bitcoin.assignment.exception.CustomBusinessEntityNotFoundException;
import com.bitcoin.assignment.exception.CustomBusinessException;
import com.bitcoin.assignment.exception.CustomBusinessValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
    private static final Logger LOG= LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

/*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleValidException1(Exception ex) {
        APIError apiError = new APIError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomBusinessValidationException.class)
    public ResponseEntity<Object> springHandleCustomBusinessValidation(CustomBusinessValidationException ex)
            throws IOException {
        APIError apiError = new APIError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<Object> springHandleCustomBusinessException(CustomBusinessException ex) throws IOException {
        APIError apiError = new APIError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomBusinessEntityNotFoundException.class)
    public ResponseEntity<Object> springHandleCustomBusinessEnityNotFound(CustomBusinessEntityNotFoundException ex)
            throws IOException {
        APIError apiError = new APIError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, HttpStatus.NOT_FOUND);
    }
*/
}