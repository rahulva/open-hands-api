package com.project.open_hands.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.project.open_hands.entity.Post;
import com.project.open_hands.entity.User;
import com.project.open_hands.repository.MessageRepository;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.repository.UserRepository;
import com.project.open_hands.resource.model.PostRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        User u1 = new User("user1@gmail.com", "User1Fname", "Lname", "test@pazz");
        userRepository.save(u1);

        User u2 = new User("user2@gmail.com", "User2Fname", "Lname", "test@pazz");
        userRepository.save(u2);

        LocalDateTime localDateTime = LocalDateTime.of(2023, 4, 15, 10, 30, 20, 0);
        Clock clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        Post post1 = new Post();
        post1.setTitle("Item Post 1");
        post1.setDescription("Item Post 1");
        post1.setCategory("Category");
        post1.setSubCategory("SubCategory");
        post1.setImages(List.of());
        post1.setLocation("");
        post1.setDateTime(Instant.now(clock).toString());
        post1.setCreatedBy("user2@gmail.com");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("Item Post 2");
        post2.setDescription("Item Post 2");
        post2.setCategory("Category");
        post2.setSubCategory("SubCategory");
        post2.setImages(List.of());
        post2.setLocation("");
        post2.setDateTime(Instant.now(clock).toString());
        post2.setCreatedBy("user2@gmail.com");
        postRepository.save(post2);


        Post post3 = new Post();
        post3.setTitle("Item Post 3");
        post3.setDescription("Item Post 3");
        post3.setCategory("Category");
        post3.setSubCategory("SubCategory");
        post3.setImages(List.of());
        post3.setLocation("");
        post3.setDateTime(Instant.now(clock).toString());
        post3.setCreatedBy("user3@gmail.com");
        postRepository.save(post3);
    }

    @AfterEach
    void after() {
        userRepository.deleteById("user1@gmail.com");
        userRepository.deleteById("user2@gmail.com");
        postRepository.delete(postRepository.title("Item Post 1"));
        postRepository.delete(postRepository.title("Item Post 2"));
        postRepository.delete(postRepository.title("Item Post 3"));
    }

    @Test
    void create() throws Exception {
        Post postByTitle = postRepository.title("Item Post 1");
//
        var userObj = """
                {
                "name": "Rah",
                "fromEmail" : "user2@gmail.com",
                "telephoneNo" : "+941233133",
                "messageText" : "Dear Rah, I like this item. I need somehitng like this/ Could you please provide me. I can share this after my use",
                "toEmail" : "user3@gmail.com",
                "requestTime" : "<request-time>",
                "postId" : {postByTitle}
                }
                """;

        String replace = userObj.replace("{postByTitle}", postByTitle.getId().toString()).replace("<request-time>", Instant.now().toString());
        mvc.perform(post("/messages").contentType("application/json").content(replace))
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(no))
                .andExpect(jsonPath("$.name").value("Rah"))
                .andExpect(jsonPath("$.fromEmail").value("user2@gmail.com"))
                .andExpect(jsonPath("$.telephoneNo").value("+941233133"))
                .andExpect(jsonPath("$.messageText").value("Dear Rah, I like this item. I need somehitng like this/ Could you please provide me. I can share this after my use"))
                .andExpect(jsonPath("$.toEmail").value("user3@gmail.com"));
    }
}