package com.project.open_hands.resource;

import com.project.open_hands.entity.Post;
import com.project.open_hands.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(post));
    }

    @GetMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Collection<Post>> getAllPosts() {
        Collection<Post> allPosts = postService.getAllPosts();
        if (allPosts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Posts available");
        }
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping(path = {"/search/"}, consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Collection<Post>> searchPostAll() {
        return getAllPosts();
    }

    @GetMapping(path = {"/search/{searchTerm}"}, consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Collection<Post>> searchPost(@PathVariable("searchTerm") String searchTerm) {
        log.info("Search term {}", searchTerm);
        if (searchTerm == null || searchTerm.isBlank() || searchTerm.equals("*")) {
            return getAllPosts();
        }

        Collection<Post> list = postService.searchPost(searchTerm);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Results found");
        }
        return ResponseEntity.ok(list);
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

    @DeleteMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Void> deleteAll() {
        Collection<Post> allPosts = postService.getAllPosts();
        allPosts.forEach(post -> postService.delete(post.getId()));
        return ResponseEntity.accepted().build();
    }
}
