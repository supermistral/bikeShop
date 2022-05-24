package com.supershaun.bikeshop.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiError {
    private int status;
    private String message;
    private List<FieldError> errors = new ArrayList<>();

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        errors.add(error);
    }
}
