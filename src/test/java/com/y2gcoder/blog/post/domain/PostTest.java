package com.y2gcoder.blog.post.domain;

import com.y2gcoder.blog.member.domain.MemberId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    @DisplayName("게시글 도메인: 생성 성공")
    void whenCreatePost_thenReturnPost() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));

        //when
        Post aut = Post.of(id, title, content, tags, author);

        //then
        assertEquals(id, aut.getId());
        assertFalse(aut.isHidden() && aut.isDeleted());
    }

    @Test
    @DisplayName("게시글 도메인: id가 없으면 생성되지 않는다.")
    void givenNullId_whenCreatePost_thenExceptionShouldBeThrown() {
        //given
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));

        //expected
        assertThrows(IllegalArgumentException.class, () -> Post.of(null, title, content, tags, author));
    }

    @Test
    @DisplayName("게시글 도메인: 제목이 없으면 생성되지 않는다.")
    void givenBlankTitle_whenCreatePost_thenExceptionShouldBeThrown() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));

        //expected
        assertThrows(IllegalArgumentException.class, () -> Post.of(id, title, content, tags, author));
    }

    @Test
    @DisplayName("게시글 도메인: 컨텐츠가 없으면 생성되지 않는다.")
    void givenNullContent_whenCreatePost_thenExceptionShouldBeThrown() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));

        //expected
        assertThrows(IllegalArgumentException.class, () -> Post.of(id, title, null, tags, author));
    }

    @Test
    @DisplayName("게시글 도메인: tag 목록이 null이면 생성되지 않는다.")
    void givenNullTags_whenCreatePost_thenExceptionShouldBeThrown() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);

        //expected
        assertThrows(IllegalArgumentException.class, () -> Post.of(id, title, content, null, author));
    }

    @Test
    @DisplayName("게시글 도메인: Author가 null이면 생성되지 않는다.")
    void givenNullAuthor_whenCreatePost_thenExceptionShouldBeThrown() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));

        //expected
        assertThrows(IllegalArgumentException.class, () -> Post.of(id, title, content, tags, null));
    }

    @Test
    @DisplayName("게시글 도메인: 제목을 바꿀 수 있다.")
    void givenNewTitle_whenChange_thenPostHasNewTitle() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));
        Post aut = Post.of(id, title, content, tags, author);

        //when
        String newTitle = "new title";
        aut.change(newTitle, content, tags);

        //then
        assertEquals(newTitle, aut.getTitle());
    }

    @Test
    @DisplayName("게시글 도메인: content를 바꿀 수 있다.")
    void givenChangedContent_whenChange_thenPostHasChangedContent() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));
        Post aut = Post.of(id, title, content, tags, author);

        //when
        PostContent newContent = new PostContent("새로운 content");
        aut.change(title, newContent, tags);

        //then
        assertEquals(newContent, aut.getContent());
    }

    @Test
    @DisplayName("게시글 도메인: 태그들을 바꿀 수 있다.")
    void givenNewTags_whenChange_thenPostHasNewTags() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = List.of(new Tag("tag1"), new Tag("tag2"));
        Post aut = Post.of(id, title, content, tags, author);

        //when
        Tag newTag1 = new Tag("newTag1");
        Tag newTag2 = new Tag("newTag2");
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag1);
        newTags.add(newTag2);
        aut.change(title, content, newTags);
        //then
        Assertions.assertThat(aut.getTags())
                .containsExactlyInAnyOrder(newTag1, newTag2);
    }

    @Test
    @DisplayName("게시글 도메인: 태그 목록 깊은 복사 테스트")
    void whenOriginalTagsClear_thenNoEffectOnPostTags() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        Post aut = Post.of(id, title, content, tags, author);

        //when
        tags.clear();

        //then
        assertEquals(2, aut.getTags().size());
    }

    @Test
    @DisplayName("게시글 도메인: 숨김 상태로 변경")
    void whenHide_thenPostIsHidden() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        Post aut = Post.of(id, title, content, tags, author);

        //when
        aut.hide();

        //then
        assertTrue(aut.isHidden());
    }

    @Test
    @DisplayName("게시글 도메인: 삭제 상태로 변경")
    void whenDelete_thenPostIsDeleted() {
        //given
        PostId id = new PostId(UUID.randomUUID().toString());
        String title = "title";
        PostContent content = new PostContent("content");
        MemberId authorId = new MemberId(UUID.randomUUID().toString());
        String authorName = "author";
        Author author = new Author(authorId, authorName);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        Post aut = Post.of(id, title, content, tags, author);

        //when
        aut.delete();

        //then
        assertTrue(aut.isDeleted());
    }
}