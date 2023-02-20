package com.y2gcoder.blog.post.application;

import com.y2gcoder.blog.post.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdatePostService {
    private final IPostRepository repository;

    public UpdatePostService(IPostRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void update(String id, String title, String contentBody, List<String> tagNames) {
        PostId postId = new PostId(id);
        Post post = repository.findById(postId)
                .orElseThrow(AggregateNotFoundException::new);

        PostContent content = new PostContent(contentBody);
        List<Tag> tags = createTags(tagNames);

        post.update(title, content, tags);
        repository.update(post);
    }

    private List<Tag> createTags(List<String> tagNames) {
        return tagNames.stream().map(Tag::new).collect(Collectors.toList());
    }
}
