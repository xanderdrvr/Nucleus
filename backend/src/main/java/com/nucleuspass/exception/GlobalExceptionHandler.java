package com.nucleuspass.exception;

import com.nucleuspass.exception.user.UserAlreadyExistsException;
import com.nucleuspass.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), Instant.now()),
                HttpStatus.CONFLICT
        );
    }

    public record ErrorResponse(String message, int status, Instant timestamp) {}
}
