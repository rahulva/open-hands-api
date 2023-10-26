package com.project.open_hands.resource;

import com.project.open_hands.entity.Image;
import com.project.open_hands.resource.model.ImageUploadResponse;
import com.project.open_hands.services.ImageService;
import com.project.open_hands.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8082") open for specific port
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(path = "/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(path = "/upload-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("postId") String postId, @RequestPart MultipartFile[] file) {
        List<Image> images = imageService.saveImages(postId, file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse(images.size() + " Images uploaded successfully"));
    }

//    @GetMapping(path = {"/{postId}"})
//    public ResponseEntity<List<byte[]>> getImage(@PathVariable("name") String postId) {
//        List<Image> images = imageService.getImages(postId);
//        List<byte[]> list = images.stream().map(image -> ImageUtility.decompressImage(image.getImageData())).toList();
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.valueOf(images.get(0).getType()))
//                .body(list);
//    }

//    @GetMapping(path = {"/get/image/{imageId}"})
//    public ResponseEntity<byte[]> getImage(@PathVariable("imageId") Long imageId) {
//        final Optional<Image> dbImage = imageService.getImages(imageId);
//
//        if (dbImage.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//        }
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.valueOf(dbImage.get().getType()))
//                .body(ImageUtility.decompressImage(dbImage.get().getImageData()));
//    }


}
