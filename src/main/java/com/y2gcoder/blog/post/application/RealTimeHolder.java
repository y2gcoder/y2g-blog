package com.y2gcoder.blog.post.application;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RealTimeHolder implements TimeHolder {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
