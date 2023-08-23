package com.project.open_hands.resource;

import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    //    @PostMapping(consumes = "application/json;charset=UTF-8")
//    public ResponseEntity<Post> create(@Valid @RequestBody Post postRequest) {
//
//        RequestMapper instance = RequestMapper.INSTANCE;
//        Post post = instance.toEntity(postRequest);
//
//        Optional<User> createdByUser = userRepo.findById(postRequest.getCreatedByEmail());
//        if (createdByUser.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid createdBy email. User not found!");
//        }
//        post.setCreatedBy(createdByUser.get());
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(postRepo.save(post));
//    }
    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepo.save(post));
    }

    @GetMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> all = postRepo.findAll();
        if (all.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Posts available");
        }
        return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/{email}", consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Collection<Post>> getAllPostForUser(@PathVariable String email) {
        Collection<Post> allForUser = postRepo.createdBy(email);
        if (allForUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Posts available");
        }
        return ResponseEntity.ok(allForUser);
    }
}
