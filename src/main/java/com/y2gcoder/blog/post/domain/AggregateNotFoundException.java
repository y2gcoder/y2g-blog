package com.y2gcoder.blog.post.domain;

public class AggregateNotFoundException extends RuntimeException {

    private static final String MESSAGE = "애그리거트를 찾을 수 없습니다.";

    public AggregateNotFoundException() {
        super(MESSAGE);
    }
}
