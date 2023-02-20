package com.y2gcoder.blog.common.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String errorCode;
    private final String errorMessage;
    private final Map<String, String> validation;

    public ErrorResponse(String errorCode, String errorMessage, Map<String, String> validation) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        validation.put(fieldName, errorMessage);
    }
}
