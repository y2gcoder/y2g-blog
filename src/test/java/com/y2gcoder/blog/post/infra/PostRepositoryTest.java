package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    PostJpaRepository jpaRepository;
    PostRepository aut;

    @BeforeEach
    void init() {
        aut = new PostRepository(jpaRepository);
    }

    @Test
    @DisplayName("nextPostId로 매번 새로운 postId를 받아올 수 있다.")
    void whenNextPostId10Times_thenAllDifferentIds() {
        //given
        List<PostId> postIds = new ArrayList<>();

        //when
        for (int i = 0; i < 10; i++) {
            postIds.add(aut.nextPostId());
        }

        //then
        Set<PostId> result = new HashSet<>(postIds);
        assertEquals(postIds.size(), result.size());
    }

    @Test
    @DisplayName("게시글 리포지토리: save() && findById()")
    void whenSave_thenFoundBySavedId() {
        //given
        PostId id = aut.nextPostId();
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        PostState state = PostState.VISIBLE;
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        LocalDateTime writtenAt = LocalDateTime.of(2023, 2, 17, 14, 4);
        Post post = Post.of(id, title, content, tags, state, author, writtenAt);

        //when
        aut.save(post);

        //then
        Optional<Post> postOptional = aut.findById(id);
        assertTrue(postOptional.isPresent());
        Post found = postOptional.get();
        assertEquals(post, found);
    }

    @Test
    @DisplayName("게시글 리포지토리: 존재하지 않는 Post의 ID로 Post를 조회하면 Optional.empty()를 반환한다.")
    void whenNotFoundByPostId_thenReturnOptionalEmpty() {
        //given
        PostId postId = aut.nextPostId();

        //when
        Optional<Post> postOptional = aut.findById(postId);

        //then
        assertTrue(postOptional.isEmpty());
    }

    @Test
    @DisplayName("게시글 리포지토리: Title 수정")
    void givenNewTitle_whenUpdate_thenUpdatedPostWithNewTitle() {
        //given
        PostId id = aut.nextPostId();
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        PostState state = PostState.VISIBLE;
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        LocalDateTime writtenAt = LocalDateTime.of(2023, 2, 20, 10, 30);
        Post post = Post.of(id, title, content, tags, state, author, writtenAt);
        PostDataModel dataModel = new PostDataModel(post);
        jpaRepository.save(dataModel);

        //when
        String newTitle = "new Title";
        post.update(newTitle, content, tags);
        aut.update(post);

        //then
        Optional<PostDataModel> postDataModelOptional = jpaRepository.findById(id.getValue());
        assertTrue(postDataModelOptional.isPresent());
        Post result = postDataModelOptional.get().toEntity();
        assertEquals(newTitle, result.getTitle());
    }

    @Test
    @DisplayName("게시글 리포지토리: content 수정")
    void givenNewContent_whenUpdate_thenUpdatedPostWithNewContent() {
        //given
        PostId id = aut.nextPostId();
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        PostState state = PostState.VISIBLE;
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        LocalDateTime writtenAt = LocalDateTime.of(2023, 2, 20, 10, 30);
        Post post = Post.of(id, title, content, tags, state, author, writtenAt);
        PostDataModel dataModel = new PostDataModel(post);
        jpaRepository.save(dataModel);

        //when
        PostContent newContent = new PostContent("newContent");
        post.update(title, newContent, tags);
        aut.update(post);

        //then
        Optional<PostDataModel> postDataModelOptional = jpaRepository.findById(id.getValue());
        assertTrue(postDataModelOptional.isPresent());
        Post result = postDataModelOptional.get().toEntity();
        assertEquals(newContent, result.getContent());
    }

    @Test
    @DisplayName("게시글 리포지토리: tags 수정")
    void givenNewTags_whenUpdate_thenUpdatedPostWithNewTags() {
        //given
        PostId id = aut.nextPostId();
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        PostState state = PostState.VISIBLE;
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        LocalDateTime writtenAt = LocalDateTime.of(2023, 2, 20, 10, 30);
        Post post = Post.of(id, title, content, tags, state, author, writtenAt);
        PostDataModel dataModel = new PostDataModel(post);
        jpaRepository.save(dataModel);

        //when
        List<Tag> newTags = Arrays.asList(new Tag("new tag 1"),
                new Tag("new tag 2"),
                new Tag("new tag 3"));
        post.update(title, content, newTags);
        aut.update(post);

        //then
        Optional<PostDataModel> postDataModelOptional = jpaRepository.findById(id.getValue());
        assertTrue(postDataModelOptional.isPresent());
        Post result = postDataModelOptional.get().toEntity();
        Assertions.assertThat(result.getTags().getTags()).containsAll(newTags);
    }

    @Test
    @DisplayName("게시글 리포지토리: 수정할 post를 찾지 못했음.")
    void givenInvalidPostId_whenUpdate_thenExceptionShouldBeThrown() {
        //given
        PostId id = aut.nextPostId();
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        PostState state = PostState.VISIBLE;
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        LocalDateTime writtenAt = LocalDateTime.of(2023, 2, 20, 10, 30);
        Post post = Post.of(id, title, content, tags, state, author, writtenAt);

        //when
        post.update(title, content, tags);
        assertThrows(AggregateNotFoundException.class, () -> aut.update(post));


    }
}