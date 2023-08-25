package com.project.open_hands.services;

import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final Map<Long, Post> cache = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        List<Post> all = postRepo.findAll();
        all.forEach(post -> cache.put(post.getId(), post));
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    public Post create(Post post) {
        Post save = postRepo.save(post);
        cache.put(save.getId(), save);
        return save;
    }

    public Collection<Post> getAllPosts() {
        return cache.values();
    }

    public Collection<Post> getAllPostForUser(String email) {
        return cache.values().stream().filter(post -> post.getCreatedBy().equals(email)).toList();
    }

    public void delete(Long postId) {
        postRepo.deleteById(postId);
        cache.remove(postId);
    }
}
