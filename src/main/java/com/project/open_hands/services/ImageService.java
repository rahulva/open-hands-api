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

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

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

        return imageRepository.saveAll(images);
    }

    public List<Image> getImages(String postId) {
        return imageRepository.findByPostId(postId);
    }
}
