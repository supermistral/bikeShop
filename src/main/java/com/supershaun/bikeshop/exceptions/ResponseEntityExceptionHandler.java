package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ApiError error = processFieldErrors(fieldErrors);

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    private ApiError processFieldErrors(List<FieldError> fieldErrors) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation error");
        for (FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtExceptionHandling(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                new DefaultMessageEntity(ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
}
