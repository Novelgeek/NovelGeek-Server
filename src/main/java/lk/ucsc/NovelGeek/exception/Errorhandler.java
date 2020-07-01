package lk.ucsc.NovelGeek.exception;


import lk.ucsc.NovelGeek.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Errorhandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleInternalErrors(Exception e) {
        System.out.println(e);
        String error= e.getMessage();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<String> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        ResponseEntity<String> response = new ResponseEntity<String>("Incorrect Username or Password", HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> elementNotFund(NoSuchElementException e) {
        System.out.println(e.getMessage());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(new ErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }



}
