package com.project.open_hands.services;

import com.project.open_hands.entity.Image;
import com.project.open_hands.repository.ImageRepository;
import com.project.open_hands.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final PostService postService;

    public List<Image> saveImages(String postId, MultipartFile[] files) {
        List<Image> images = Arrays.stream(files).map(file -> {
            try {
                return Image.builder()
                        .postId(postId)
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(ImageUtility.compressImage(file.getBytes())).build();
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }).toList();

        List<Image> images1 = imageRepository.saveAll(images);
        postService.notifyImageUpload(images1);
        return images1;
    }
/*
    public List<Image> getImages(String postId) {
        return imageRepository.findByPostId(postId);
    }

    public Optional<Image> getImages(Long imageId) {
        return imageRepository.findById(imageId);
    }*/


}
