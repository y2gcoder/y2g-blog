package com.y2gcoder.blog.post.infra;

import com.y2gcoder.blog.post.domain.Tag;
import com.y2gcoder.blog.post.domain.Tags;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagsConverter implements AttributeConverter<Tags, String> {
    @Override
    public String convertToDatabaseColumn(Tags attribute) {
        if (attribute == null) return null;
        return attribute.getTags().stream().map(Tag::getName)
                .collect(Collectors.joining(","));
    }

    @Override
    public Tags convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String[] split = dbData.split(",");
        List<Tag> tags = Arrays.stream(split)
                .map(Tag::new)
                .toList();
        return new Tags(tags);
    }
}
