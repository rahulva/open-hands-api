package com.project.open_hands.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.project.open_hands.Constants.UTC;
import static com.project.open_hands.Constants.YYYY_MM_DD_HH_MM_SS;

@Getter
@Setter
@ToString
@Entity
@Table(name = "oh_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String fromEmail;

    private String telephoneNo;

    private String messageText;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS, timezone = UTC)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime requestTime;

    private String toEmail;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
