package com.project.open_hands.services;

import com.project.open_hands.entity.Image;
import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.ImageRepository;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.util.ImageUtility;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final ImageRepository imageRepo;
    private final Map<Long, Post> cache = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        List<Post> all = postRepo.findAll();
        all.forEach(post -> cache.put(post.getId(), post));
        List<Image> images = imageRepo.findAll();
        getDisplayReadyImages(images)
                .forEach(image -> cache.get(Long.valueOf(image.getPostId())).getImages().add(image));
    }

    private static Stream<Image> getDisplayReadyImages(List<Image> images) {
        return images.stream()
                .map(image ->
                        Image.builder()
                                .id(image.getId())
                                .postId(image.getPostId())
                                .type(image.getType())
                                .name(image.getName())
                                .imageData(ImageUtility.decompressImage(image.getImageData()))
                                .build());
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

    public void notifyImageUpload(List<Image> images) {
        cache.computeIfPresent(Long.valueOf(images.get(0).getPostId()), (aLong, post) -> {
            post.getImages().addAll(getDisplayReadyImages(images).toList());
            return post;
        });
    }
}
