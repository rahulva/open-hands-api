package com.project.open_hands.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    /*@Temporal(TemporalType.TIMESTAMP)*/
    /*private Instant timestamp;*/
    private String dateTime;

    @Column(nullable = false)
    /*@ManyToOne
    @JoinColumn(name = "created_by_email")
    private User createdBy;*/
    private String createdBy;
}
