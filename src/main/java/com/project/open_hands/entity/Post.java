package com.project.open_hands.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.open_hands.Constants.UTC;
import static com.project.open_hands.Constants.YYYY_MM_DD_HH_MM_SS;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "oh_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String category;

    @NotBlank
    @Column(nullable = false, name = "item_condition")
    private String condition;

    private String location;

    @Transient
    private List<Image> images = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS, timezone = UTC)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String createdBy;
}
