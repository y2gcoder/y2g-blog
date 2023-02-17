package com.y2gcoder.blog.post.domain;

import org.springframework.util.StringUtils;

import java.util.Objects;

class Tag {
    private final String name;

    public Tag(String name) {
        if (!StringUtils.hasText(name)) throw new IllegalArgumentException("no post tag name");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
