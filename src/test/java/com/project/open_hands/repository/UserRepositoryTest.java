package com.project.open_hands.repository;

import com.project.open_hands.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void simpleTest() {
        User entity = new User();
        entity.setFirstName("fname");
        entity.setLastName("lass");
        entity.setEmail("test@gmail.com");
        entity.setPassword("pass");
        User user = repository.save(entity);
        assertThat(user).isNotNull();
        System.out.println("user = " + user);

        Optional<User> fromDb = repository.findById(user.getEmail());
        assertThat(user).isEqualTo(fromDb.get());
    }
}