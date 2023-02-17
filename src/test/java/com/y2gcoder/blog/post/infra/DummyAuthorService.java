package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.Author;
import com.y2gcoder.blog.post.domain.IAuthorService;

public class DummyAuthorService implements IAuthorService {
    @Override
    public Author createAuthor(MemberId authorId) {
        return new Author(authorId, "문파관작");
    }
}
