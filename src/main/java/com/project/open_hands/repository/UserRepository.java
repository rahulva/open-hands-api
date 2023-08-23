package com.project.open_hands.repository;

import com.project.open_hands.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
