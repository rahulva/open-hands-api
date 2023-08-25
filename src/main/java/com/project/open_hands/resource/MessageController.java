package com.project.open_hands.resource;

import com.project.open_hands.entity.Message;
import com.project.open_hands.entity.Post;
import com.project.open_hands.mappers.MessageMapper;
import com.project.open_hands.repository.MessageRepository;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.resource.model.MessageRequest;
import com.project.open_hands.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepo;
    private final PostService postService;

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Message> create(@RequestBody @Valid MessageRequest messageRequest) {
        Optional<Post> post = postService.findById(messageRequest.getPostId());
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Post doesn't exists");
        }
        /*FIXME Message entity = MessageMapper.INSTANCE.toEntity(messageRequest);*/
        Message entity = toMessage(messageRequest, post.get());
        System.out.println("entity = " + entity);
        Message msg = messageRepo.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    private static Message toMessage(MessageRequest messageRequest, Post post) {
        Message entity = new Message();
        entity.setName(messageRequest.getName());
        entity.setMessageText(messageRequest.getMessageText());
        entity.setFromEmail(messageRequest.getFromEmail());
        entity.setToEmail(messageRequest.getToEmail());
//        entity.setRequestTime(Instant.parse(messageRequest.getRequestTime())); //TODO date time converter
        entity.setRequestTime(messageRequest.getRequestTime());
        entity.setTelephoneNo(messageRequest.getTelephoneNo());
        entity.setPost(post);
        return entity;
    }

    @GetMapping("/{fromOrTo}/{email}")
    public ResponseEntity<List<Message>> getMessages(String fromOrTo, String email) {
        if ("from".equals(fromOrTo)) {
            return ResponseEntity.ok(messageRepo.findByFromEmail(email));
        }
        return ResponseEntity.ok(messageRepo.findByToEmail(email));
    }
}
