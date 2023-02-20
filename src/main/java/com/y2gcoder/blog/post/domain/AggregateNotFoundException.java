package com.y2gcoder.blog.post.domain;

import com.y2gcoder.blog.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AggregateNotFoundException extends BusinessException {

    private static final String MESSAGE = "애그리거트를 찾을 수 없습니다.";

    public AggregateNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getErrorCode() {
        return HttpStatus.BAD_REQUEST.toString();
    }
}
