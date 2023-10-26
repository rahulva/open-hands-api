package com.project.open_hands.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.open_hands.entity.Post;
import com.project.open_hands.entity.User;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.repository.UserRepository;
import com.project.open_hands.services.PostService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Test
    void create() throws Exception {
        Post post = createPost();

        var userObj = """
                {
                "name": "Rah",
                "fromEmail" : "user2@gmail.com",
                "telephoneNo" : "+941233133",
                "messageText" : "Dear Rah, I like this item. I need something like this/ Could you please provide me. I can share this after my use",
                "toEmail" : "user3@gmail.com",
                "requestTime" : "2023-09-16 12:32:29.603947",
                "postId" : <id>
                }
                """;

        mvc.perform(post("/messages").contentType("application/json").content(userObj.replace("<id>", post.getId().toString())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Rah"))
                .andExpect(jsonPath("$.fromEmail").value("user2@gmail.com"))
                .andExpect(jsonPath("$.telephoneNo").value("+941233133"))
                .andExpect(jsonPath("$.messageText").value("Dear Rah, I like this item. I need something like this/ Could you please provide me. I can share this after my use"))
                .andExpect(jsonPath("$.toEmail").value("user3@gmail.com"));
    }

    private Post createPost() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 4, 15, 10, 30, 20, 0);
        Clock clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        Post post1 = new Post();
        post1.setTitle("Item Post 4");
        post1.setDescription("Item Post 1");
        post1.setCategory("Category");
        post1.setCondition("New");
        post1.setImages(List.of());
        post1.setLocation("");
        post1.setDateTime(LocalDateTime.now(clock));
        post1.setCreatedBy("user2@gmail.com");

        String contentAsString = mvc.perform(post("/posts").contentType("application/json").content(mapper.writeValueAsString(post1)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        return mapper.readValue(contentAsString, Post.class);
    }

}