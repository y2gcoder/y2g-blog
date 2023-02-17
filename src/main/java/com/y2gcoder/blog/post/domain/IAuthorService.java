package com.y2gcoder.blog.post.domain;

import com.y2gcoder.blog.member.domain.MemberId;

public interface IAuthorService {
    Author createAuthor(MemberId authorId);
}
