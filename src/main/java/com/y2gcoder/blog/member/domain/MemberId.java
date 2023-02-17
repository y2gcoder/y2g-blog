package com.y2gcoder.blog.member.domain;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class MemberId {
    private final String value;

    public MemberId(String value) {
        if (!StringUtils.hasText(value)) throw new IllegalArgumentException("no member id");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(value, memberId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
