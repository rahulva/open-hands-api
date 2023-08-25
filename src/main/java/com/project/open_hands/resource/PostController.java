package com.project.open_hands.resource;

import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.ImageRepository;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.repository.UserRepository;
import com.project.open_hands.services.PostService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ImageRepository imageRepository;

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(post));
    }

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Post> createMultipart(@Valid @RequestPart Post post, @RequestPart MultipartFile[] file) {
//        Post post1 = postService.create(post);
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(post1);
//    }

    @GetMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Collection<Post>> getAllPosts() {
        if (postService.getAllPosts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Posts available");
        }
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping(path = "/{email}", consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Collection<Post>> getAllPostForUser(@PathVariable String email) {
        Collection<Post> list = postService.getAllPostForUser(email);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Posts available");
        }
        return ResponseEntity.ok(list);
    }

    @DeleteMapping(path = "/{postId}", consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.accepted().build();
    }

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
}
