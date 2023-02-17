package com.y2gcoder.blog.post.domain;

import com.y2gcoder.blog.member.domain.MemberId;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class Author {
    private final MemberId id;
    private final String name;

    public Author(MemberId id, String name) {
        if (id == null) throw new IllegalArgumentException("no author id");
        this.id = id;
        if (!StringUtils.hasText(name)) throw new IllegalArgumentException("no author name");
        this.name = name;
    }

    public MemberId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
