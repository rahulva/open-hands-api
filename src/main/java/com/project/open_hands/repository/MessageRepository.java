package com.project.open_hands.repository;

import com.project.open_hands.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByToEmail(String email);

    List<Message> findByFromEmail(String email);
}
