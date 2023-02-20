package com.y2gcoder.blog.post.application;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.*;
import com.y2gcoder.blog.post.infra.SpyPostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class UpdatePostServiceTest {

    private SpyPostRepository repository;

    private UpdatePostService sut;

    @BeforeEach
    void init() {
        repository = new SpyPostRepository();
        sut = new UpdatePostService(repository);
    }

    @Test
    @DisplayName("Unit Test:  UpdatePostService update()")
    void whenUpdate_thenSuccess() {
        //given
        PostId id = repository.nextPostId();
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        PostState state = PostState.VISIBLE;
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        LocalDateTime writtenAt = LocalDateTime.of(2023, 2, 20, 11, 5);
        Post post = Post.of(id, title, content, tags, state, author, writtenAt);
        repository.save(post);

        //when
        String newTitle = "newTitle";
        String newContentBody = "newContent";
        List<String> newTagNames = IntStream.range(1, 11)
                .mapToObj(operand -> "new tag " + operand).toList();
        sut.update(id.getValue(), newTitle, newContentBody, newTagNames);

        //then
        Optional<Post> postOptional = repository.findById(id);
        assertTrue(postOptional.isPresent());
        Post result = postOptional.get();
        assertEquals(newTitle, result.getTitle());
        assertEquals(newContentBody, result.getContent().getContent());
        List<Tag> resultTags = result.getTags().getTags();
        assertEquals(newTagNames.size(), resultTags.size());
        List<String> resultTagNames = resultTags.stream().map(Tag::getName).toList();
        Assertions.assertThat(resultTagNames).containsAll(newTagNames);
    }

}