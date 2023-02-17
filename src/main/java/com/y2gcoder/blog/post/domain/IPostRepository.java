package com.y2gcoder.blog.post.domain;

import java.util.Optional;
import java.util.UUID;

public interface IPostRepository {
    void save(Post post);

    Optional<Post> findById(PostId id);

    default PostId nextPostId() {
        return new PostId(UUID.randomUUID().toString());
    }
}
