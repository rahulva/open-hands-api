package com.project.open_hands.repository;

import com.project.open_hands.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PostRepository extends JpaRepository<Post, Long> {

    Collection<Post> createdBy(String email);
    Post title(String title);
}
