package com.y2gcoder.blog.post.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.application.WritePostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WritePostService writePostService;


    @Test
    @DisplayName("POST 게시글 작성")
    void whenWrite() throws Exception {
        //given
        List<String> tagNames = Arrays.asList("tag1", "tag2", "tag3", "tag4");
        String title = "title";
        String content = "content";
        PostWrite request = new PostWrite(title, content, tagNames);

        //expected
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(writePostService, times(1)).write(eq(title), eq(content), eq(tagNames), any(MemberId.class));
    }

    @Test
    @DisplayName("POST 게시글 작성 - 제목이 공백일 때")
    void givenWhitespaceTitle_whenWrite_thenReturnErrorResponse() throws Exception {
        //given
        List<String> tagNames = Arrays.asList("tag1", "tag2", "tag3", "tag4");
        String title = "       ";
        String content = "content";
        PostWrite request = new PostWrite(title, content, tagNames);

        //expected
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.errorMessage").value("유효성 검증에 실패하셨습니다."))
                .andExpect(jsonPath("$.validation.title").exists());
    }

    @Test
    @DisplayName("POST 게시글 작성 - 내용이 공백일 때")
    void givenWhitespaceContent_whenWrite_thenReturnErrorResponse() throws Exception {
        //given
        List<String> tagNames = Arrays.asList("tag1", "tag2", "tag3", "tag4");
        String title = "title";
        String content = "                ";
        PostWrite request = new PostWrite(title, content, tagNames);

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.errorMessage").value("유효성 검증에 실패하셨습니다."))
                .andExpect(jsonPath("$.validation.content").exists());
    }

    @Test
    @DisplayName("POST 게시글 작성 - 리스트가 null일 때")
    void givenNullTagNames_whenWrite_thenReturnErrorResponse() throws Exception {
        //given
        Map<String, Object> request = new HashMap<>();
        request.put("title", "title");
        request.put("content", "content");

        //expected
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.errorMessage").value("유효성 검증에 실패하셨습니다."))
                .andExpect(jsonPath("$.validation.tags").exists());
    }
}