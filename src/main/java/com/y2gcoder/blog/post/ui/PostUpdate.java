package com.y2gcoder.blog.post.ui;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostUpdate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private List<String> tags;
}
