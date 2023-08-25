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

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8082") open for specific port
@CrossOrigin
@RequestMapping(path = "/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;


//    @PostMapping("/upload")
//    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("postId") String postId, @RequestPart MultipartFile file)
//            throws IOException {
//        MultipartFile[] files = new MultipartFile[]{file};
//        imageService.extracted(postId, files);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ImageUploadResponse("Image uploaded successfully: " +
//                        file.getOriginalFilename()));
//    }

    @PostMapping(path = "/upload-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("postId") String postId, @RequestPart MultipartFile[] file) {
        imageService.saveImages(postId, file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Images uploaded successfully"));
    }

    @GetMapping(path = {"/{postId}"})
    public ResponseEntity<List<byte[]>> getImage(@PathVariable("name") String postId) throws IOException {
        List<Image> images = imageService.getImages(postId);
        List<byte[]> list = images.stream().map(image -> ImageUtility.decompressImage(image.getImageData())).toList();

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(images.get(0).getType()))
                .body(list);
    }


}
