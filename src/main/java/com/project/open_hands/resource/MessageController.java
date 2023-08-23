package com.project.open_hands.resource;

import com.project.open_hands.entity.Message;
import com.project.open_hands.entity.Post;
import com.project.open_hands.mappers.MessageMapper;
import com.project.open_hands.repository.MessageRepository;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.resource.model.MessageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepo;
    private final PostRepository postRepo;

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Message> create(@Valid @RequestBody MessageRequest messageRequest) {
        System.out.println("messageRequest = " + messageRequest);
        Optional<Post> post = postRepo.findById(messageRequest.getPostId());
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Post doesn't exists");
        }
        /*FIXME Message entity = MessageMapper.INSTANCE.toEntity(messageRequest);*/
        Message entity = new Message();
        entity.setName(messageRequest.getName());
        entity.setMessageText(messageRequest.getMessageText());
        entity.setFromEmail(messageRequest.getFromEmail());
        entity.setToEmail(messageRequest.getToEmail());
        entity.setRequestTime(Instant.parse(messageRequest.getRequestTime()));
        entity.setTelephoneNo(messageRequest.getTelephoneNo());
        entity.setPost(post.get());
        System.out.println("entity = " + entity);
        Message msg = messageRepo.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
}
