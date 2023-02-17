package com.y2gcoder.blog.post.application;

import java.time.LocalDateTime;

public class StubTimeHolder implements TimeHolder {
    private final LocalDateTime currentTime;

    public StubTimeHolder(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public LocalDateTime now() {
        return currentTime;
    }
}
