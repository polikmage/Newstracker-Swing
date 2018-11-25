package org.mpo.newstracker.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.mpo.newstracker.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionHandlingController {
    private static Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);


    @ExceptionHandler(BackendException.class)
    public ResponseEntity<ExceptionResponse> handleConnectionToBackendFailedException(BackendException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getStatusCode(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.valueOf(e.getStatusCode()));
    }
    @ExceptionHandler(NoArticlesFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoArticlesFoundException(NoArticlesFoundException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getStatusCode(),e.getMessage());
        log.warn(e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCountryNotFoundException(CountryNotFoundException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getStatusCode(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(e.getStatusCode()));
    }
    @ExceptionHandler(TranslatorException.class)
    public ResponseEntity<ExceptionResponse> handleTranslatorException(TranslatorException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getStatusCode(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(e.getStatusCode()));
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ExceptionResponse> handleHttpClientErrorException(HttpClientErrorException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getStatusCode().value(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(e.getStatusCode().value()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.value(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getStatusCode()));
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getStatusCode()));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        //exceptionResponse.setStatusCode(e.getStatusCode());
        log.warn(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getStatusCode()));
    }
}
