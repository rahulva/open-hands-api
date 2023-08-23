package com.project.open_hands.resource.model;

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
    private String subCategory;
    private String location;
    private List<String> images = new ArrayList<>();

    private String timestamp;
    private String createdByEmail;
}
