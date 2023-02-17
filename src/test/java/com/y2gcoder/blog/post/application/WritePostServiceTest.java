package com.y2gcoder.blog.post.application;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.Post;
import com.y2gcoder.blog.post.infra.DummyAuthorService;
import com.y2gcoder.blog.post.infra.SpyPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WritePostServiceTest {

    private SpyPostRepository repository;
    private StubTimeHolder timeHolder;
    private WritePostService sut;

    @BeforeEach
    void init() {
        repository = new SpyPostRepository();
        LocalDateTime currentTime = LocalDateTime.of(2023, 2, 17, 15, 35);
        timeHolder = new StubTimeHolder(currentTime);
        sut = new WritePostService(repository,
                new DummyAuthorService(),
                timeHolder);
    }

    @Test
    @DisplayName("Unit Test: WritePostService write()")
    void whenWrite_thenSuccess() {
        //given
        String title = "title";
        String content = "content";
        List<String> tags = IntStream.range(1, 6)
                .mapToObj(operand -> "tag" + operand).toList();
        MemberId authorId = new MemberId("testAuthorId");

        //when
        sut.write(title, content, tags, authorId);

        //then
        Post actual = repository.getLastSavedPost();
        assertEquals(title, actual.getTitle());
        assertEquals(authorId, actual.getAuthor().getId());
        assertEquals(timeHolder.now(), actual.getWrittenAt());
    }



}