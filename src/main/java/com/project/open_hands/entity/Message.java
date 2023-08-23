package com.project.open_hands.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@Entity
@Table(name = "oh_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @JoinColumn(name = "from_user_email")
    private String fromEmail;

    private String telephoneNo;

    private String messageText;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant requestTime;

    @JoinColumn(name = "to_user_email")
    private String toEmail;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
