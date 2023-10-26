package com.project.open_hands.resource;

import com.project.open_hands.Constants;
import com.project.open_hands.entity.Message;
import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.MessageRepository;
import com.project.open_hands.resource.model.MessageRequest;
import com.project.open_hands.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageRepository messageRepo;
    private final PostService postService;

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Message> create(@RequestBody @Valid MessageRequest messageRequest) {
        Optional<Post> post = postService.findById(messageRequest.getPostId());
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Post doesn't exists");
        }
        Message entity = toMessage(messageRequest, post.get());
        Message msg = messageRepo.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/from/{email}")
    public ResponseEntity<List<Message>> getMessagesFrom(@Valid @PathVariable("email") String email) {
        // Request that I made to other (my email will be in from field)
        log.info("Requesting from, {}", email);
        List<Message> messages = messageRepo.findByFromEmail(email);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No data from" + " - " + email);
        }

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/to/{email}")
    public ResponseEntity<List<Message>> getMessages(@Valid @PathVariable("email") String email) {
        log.info("Requesting to, {}", email);
        List<Message> messages = messageRepo.findByToEmail(email);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No data to " + email);
        }

        return ResponseEntity.ok(messages);
    }

    private static Message toMessage(MessageRequest messageRequest, Post post) {
        Message entity = new Message();
        entity.setName(messageRequest.getName());
        entity.setMessageText(messageRequest.getMessageText());
        entity.setFromEmail(messageRequest.getFromEmail());
        entity.setToEmail(messageRequest.getToEmail());
        entity.setRequestTime(LocalDateTime.parse(messageRequest.getRequestTime(), Constants.dateTimeFormatter));
        entity.setTelephoneNo(messageRequest.getTelephoneNo());
        entity.setPost(post);
        return entity;
    }
}
