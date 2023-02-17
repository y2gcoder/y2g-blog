package com.y2gcoder.blog.post.application;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WritePostService {

    private final IPostRepository repository;
    private final IAuthorService authorService;
    private final TimeHolder timeHolder;

    public WritePostService(IPostRepository repository, IAuthorService authorService, TimeHolder timeHolder) {
        this.repository = repository;
        this.authorService = authorService;
        this.timeHolder = timeHolder;
    }

    public void write(String title, String contentBody, List<String> tagNames, MemberId authorId) {
        PostContent content = new PostContent(contentBody);
        List<Tag> tags = createTags(tagNames);

        PostId id = repository.nextPostId();
        Author author = authorService.createAuthor(authorId);
        LocalDateTime writtenAt = timeHolder.now();

        Post post = Post.of(id, title, content, tags, PostState.VISIBLE, author, writtenAt);
        repository.save(post);
    }

    private List<Tag> createTags(List<String> tagNames) {
        return tagNames.stream().map(Tag::new).collect(Collectors.toList());
    }


}
