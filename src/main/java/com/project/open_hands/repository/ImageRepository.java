package com.project.open_hands.repository;

import com.project.open_hands.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPostId(String postId);
}
