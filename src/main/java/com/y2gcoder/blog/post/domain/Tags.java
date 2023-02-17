package com.y2gcoder.blog.post.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tags {
    private final List<Tag> tags;

    public Tags(List<Tag> tags) {
        if (tags == null) throw new IllegalArgumentException("no tags");
        this.tags = new ArrayList<>(tags);
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }
}



