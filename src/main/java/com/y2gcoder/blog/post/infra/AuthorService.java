package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.member.domain.MemberId;
import com.y2gcoder.blog.post.domain.Author;
import com.y2gcoder.blog.post.domain.IAuthorService;
import org.springframework.stereotype.Component;

@Component
public class AuthorService implements IAuthorService {
    //TODO
    @Override
    public Author createAuthor(MemberId authorId) {
        return new Author(authorId, "문파관작");
    }
}
