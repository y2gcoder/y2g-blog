package com.y2gcoder.blog.post.domain;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Post {
    private final PostId id;
    private String title;
    private PostContent content;
    private List<Tag> tags;
    private PostState state;
    private final Author author;
    private final LocalDateTime writtenAt;


    private Post(PostId id,
                 String title,
                 PostContent content,
                 List<Tag> tags,
                 PostState state,
                 Author author,
                 LocalDateTime writtenAt) {
        if (id == null) throw new IllegalArgumentException("no postId");
        this.id = id;
        setTitle(title);
        setContent(content);
        setTags(tags);
        this.state = state;
        if (author == null) throw new IllegalArgumentException("no author");
        this.author = author;
        this.writtenAt = writtenAt;
    }

    public static Post of(PostId id,
                          String title,
                          PostContent content,
                          List<Tag> tags,
                          Author author) {
        return new Post(
                id,
                title,
                content,
                tags,
                PostState.VISIBLE,
                author,
                LocalDateTime.now()
        );
    }

    public void change(String title,
                       PostContent content,
                       List<Tag> tags) {
        setTitle(title);
        setContent(content);
        setTags(tags);
    }

    public void hide() {
        this.state = PostState.HIDDEN;
    }

    public void delete() {
        this.state = PostState.DELETED;
    }

    public boolean isHidden() {
        return this.state == PostState.HIDDEN;
    }

    public boolean isDeleted() {
        return this.state == PostState.DELETED;
    }

    public PostId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public PostContent getContent() {
        return content;
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    private void setTitle(String title) {
        if (!StringUtils.hasText(title)) throw new IllegalArgumentException("no title");
        this.title = title;
    }

    private void setContent(PostContent content) {
        if (content == null) throw new IllegalArgumentException("no post content");
        this.content = content;
    }

    private void setTags(List<Tag> tags) {
        if (tags == null) throw new IllegalArgumentException("no tags");
        this.tags = new ArrayList<>(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
