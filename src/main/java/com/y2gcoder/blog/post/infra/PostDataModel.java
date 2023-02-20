package com.y2gcoder.blog.post.infra;


import com.y2gcoder.blog.common.infra.jpa.BaseTimeEntity;
import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class PostDataModel extends BaseTimeEntity {
    @Id
    private String id;

    private String title;

    private String content;

    @Column(name = "tags")
    @Convert(converter = TagsConverter.class)
    private Tags tags;

    @Enumerated(EnumType.STRING)
    private PostState state;

    private String authorId;
    private String authorName;

    private LocalDateTime writtenAt;


    public PostDataModel(Post post) {
        this.id = post.getId().getValue();
        this.title = post.getTitle();
        this.content = post.getContent().getContent();
        this.tags = post.getTags();
        this.state = post.getState();
        this.authorId = post.getAuthor().getId().getValue();
        this.authorName = post.getAuthor().getName();
        this.writtenAt = post.getWrittenAt();
    }

    public Post toEntity() {
        return Post.of(
                new PostId(id),
                title,
                new PostContent(content),
                tags.getTags(),
                state,
                new Author(new MemberId(authorId), authorName),
                writtenAt
        );
    }

    public void update(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent().getContent();
        this.tags = post.getTags();
    }
}
