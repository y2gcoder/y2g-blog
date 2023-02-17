package com.y2gcoder.blog.post.domain;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class PostId {
    private final String value;

    public PostId(String value) {
        if (!StringUtils.hasText(value)) throw new IllegalArgumentException("no post id");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostId postId = (PostId) o;
        return Objects.equals(value, postId.value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
