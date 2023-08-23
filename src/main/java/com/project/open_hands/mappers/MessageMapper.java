package com.project.open_hands.mappers;

import com.project.open_hands.entity.Message;
import com.project.open_hands.entity.Post;
import com.project.open_hands.resource.model.MessageRequest;
import com.project.open_hands.resource.model.PostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "post", ignore = true)
    Message toEntity(MessageRequest messageRequest);
}
