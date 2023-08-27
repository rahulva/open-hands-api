package com.project.open_hands.resource.model;

import com.project.open_hands.entity.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String condition;
    private String location;
    private List<Image> images = new ArrayList<>();

    private String timestamp;
    private String createdByEmail;
}
