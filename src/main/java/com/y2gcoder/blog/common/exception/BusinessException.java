package com.y2gcoder.blog.common.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public abstract class BusinessException extends RuntimeException {

    @Getter
    private final Map<String, String> validation = new HashMap<>();

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getErrorCode();

    public void addValidation(String fieldName, String errorMessage) {
        validation.put(fieldName, errorMessage);
    }
}
