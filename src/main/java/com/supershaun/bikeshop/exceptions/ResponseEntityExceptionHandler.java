package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new DefaultMessageEntity(exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({ EmailAlreadyInUseException.class })
    public ResponseEntity<?> authExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new DefaultMessageEntity(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<?> tokenRefreshExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new DefaultMessageEntity(exception.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler({ CategoryNotFoundException.class, ItemNotFoundException.class })
    public ResponseEntity<?> categoryNotFoundExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new DefaultMessageEntity(exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
