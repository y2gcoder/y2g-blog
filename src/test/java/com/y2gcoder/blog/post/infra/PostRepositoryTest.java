package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.*;
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
}