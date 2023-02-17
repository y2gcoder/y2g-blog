package com.y2gcoder.blog.post.domain;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class PostContent {
    private final String content;

    public PostContent(String content) {
        if (!StringUtils.hasText(content)) throw new IllegalArgumentException("no contents");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostContent that = (PostContent) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
