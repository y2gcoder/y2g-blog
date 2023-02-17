package com.y2gcoder.blog.post.infra;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PostJpaRepository extends Repository<PostDataModel, String> {
    void save(PostDataModel dataModel);

    Optional<PostDataModel> findById(String id);
}
