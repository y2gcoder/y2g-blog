package com.y2gcoder.blog.post.ui;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.application.WritePostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/posts")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final WritePostService writePostService;

    @PostMapping
    public ResponseEntity<Void> write(@Valid @RequestBody PostWrite request) {
        //TODO AuthorId
        MemberId authorId = new MemberId("AuthorId");
        writePostService.write(request.getTitle(), request.getContent(), request.getTags(), authorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
