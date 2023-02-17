package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.post.domain.IPostRepository;
import com.y2gcoder.blog.post.domain.Post;
import com.y2gcoder.blog.post.domain.PostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository implements IPostRepository {

    private final PostJpaRepository jpaRepository;

    @Override
    public void save(Post post) {
        PostDataModel dataModel = new PostDataModel(post);
        jpaRepository.save(dataModel);
    }

    @Override
    public Optional<Post> findById(PostId id) {
        return jpaRepository.findById(id.getValue()).map(PostDataModel::toEntity);
    }
}
