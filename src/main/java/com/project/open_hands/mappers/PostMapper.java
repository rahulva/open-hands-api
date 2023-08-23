package com.project.open_hands.mappers;

import com.project.open_hands.entity.Post;
import com.project.open_hands.resource.model.PostRequest;
import org.mapstruct.factory.Mappers;

public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toEntity(PostRequest postRequest);
}
