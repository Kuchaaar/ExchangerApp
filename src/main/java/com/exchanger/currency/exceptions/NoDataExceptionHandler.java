package com.exchanger.currency.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class NoDataExceptionHandler {
    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<Object> customExceptionHandler(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>("No data", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
