package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.post.domain.IPostRepository;
import com.y2gcoder.blog.post.domain.Post;
import com.y2gcoder.blog.post.domain.PostId;

import java.util.*;

public class SpyPostRepository implements IPostRepository {
    private Post lastSavedPost;
    private static final List<Post> posts = new ArrayList<>();

    @Override
    public void save(Post post) {
        if (findById(post.getId()).isEmpty()) {
            posts.add(post);
            lastSavedPost = post;
        }
    }

    @Override
    public Optional<Post> findById(PostId id) {
        for (Post post : posts) {
            if (post.getId().equals(id)) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public Post getLastSavedPost() {
        return lastSavedPost;
    }
}
