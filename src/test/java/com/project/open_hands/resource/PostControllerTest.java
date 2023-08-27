package com.project.open_hands.resource;

import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {

        LocalDateTime localDateTime = LocalDateTime.of(2023, 4, 15, 10, 30, 20, 0);
        Clock clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        Post post1 = new Post();
        post1.setTitle("Item Post 4");
        post1.setDescription("Item Post 1");
        post1.setCategory("Category");
        post1.setCondition("New");
        post1.setImages(List.of());
        post1.setLocation("");
        post1.setDateTime(Instant.now(clock).toString());
        post1.setCreatedBy("user2@gmail.com");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("Item Post 5");
        post2.setDescription("Item Post 2");
        post2.setCategory("Category");
        post2.setCondition("New");
        post2.setImages(List.of());
        post2.setLocation("");
        post2.setDateTime(Instant.now(clock).toString());
        post2.setCreatedBy("user2@gmail.com");
        postRepository.save(post2);


        Post post3 = new Post();
        post3.setTitle("Item Post 6");
        post3.setDescription("Item Post 3");
        post3.setCategory("Category");
        post3.setCondition("New");
        post3.setImages(List.of());
        post3.setLocation("");
        post3.setDateTime(Instant.now(clock).toString());
        post3.setCreatedBy("user3@gmail.com");
        postRepository.save(post3);
    }

    @AfterEach
    void after() {
        postRepository.delete(postRepository.title("Item Post 4"));
        postRepository.delete(postRepository.title("Item Post 5"));
        postRepository.delete(postRepository.title("Item Post 6"));
    }

    @Test
    void create() throws Exception {
        var userObj = """
                {
                "title": "Razer balde",
                "description" : "This razer blade is brand new and unused. I bouth it and never get chance to use it.",
                "category" : "Self care",
                "condition" : "Facial products",
                "location" : "Frankfurt",
                "images" : ["test_img","test_img2"],
                "dateTime" : "2023-10-11 10:20:30",
                "createdBy" : "test2@gmail.com"
                }
                """;
        mvc.perform(post("/posts").contentType("application/json").content(userObj))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.description").value("This razer blade is brand new and unused. I bouth it and never get chance to use it."))
                .andExpect(jsonPath("$.category").value("Self care"))
                .andExpect(jsonPath("$.location").value("Frankfurt"))
                .andExpect(jsonPath("$.createdBy").value("test2@gmail.com"));

        postRepository.delete(postRepository.title("Razer balde"));
    }

    @Test
    void getAllPost() throws Exception {
        String contentAsString = mvc.perform(get("/posts").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // [{"id":1,"title":"Item Post 1","description":"Item Post 1","category":"Category","condition":"New","location":"","images":[],"dateTime":"2023-04-15T10:30:20Z","createdBy":"user2@gmail.com"},{"id":2,"title":"Item Post 2","description":"Item Post 2","category":"Category","condition":"New","location":"","images":[],"dateTime":"2023-04-15T10:30:20Z","createdBy":"user2@gmail.com"},{"id":3,"title":"Item Post 3","description":"Item Post 3","category":"Category","condition":"New","location":"","images":[],"dateTime":"2023-04-15T10:30:20Z","createdBy":"user3@gmail.com"}]
        String res = "[{\"id\":1,\"title\":\"Item Post 1\",\"description\":\"Item Post 1\",\"category\":\"Category\",\"condition\":\"New\",\"location\":\"\",\"images\":[],\"dateTime\":\"2023-04-15T10:30:20Z\",\"createdBy\":\"user2@gmail.com\"},{\"id\":2,\"title\":\"Item Post 2\",\"description\":\"Item Post 2\",\"category\":\"Category\",\"condition\":\"New\",\"location\":\"\",\"images\":[],\"dateTime\":\"2023-04-15T10:30:20Z\",\"createdBy\":\"user2@gmail.com\"},{\"id\":3,\"title\":\"Item Post 3\",\"description\":\"Item Post 3\",\"category\":\"Category\",\"condition\":\"New\",\"location\":\"\",\"images\":[],\"dateTime\":\"2023-04-15T10:30:20Z\",\"createdBy\":\"user3@gmail.com\"}]";
        Assertions.assertThat(contentAsString).isEqualTo(res.trim());
    }

    @Test
    void getAllPostForUser() throws Exception {
        String contentAsString = mvc.perform(get("/posts/user2@gmail.com").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //[{"id":1,"title":"Item Post 1","description":"Item Post 1","category":"Category","condition":"New","location":"","images":[],"dateTime":"2023-04-15T10:30:20Z","createdBy":"user2@gmail.com"},{"id":2,"title":"Item Post 2","description":"Item Post 2","category":"Category","condition":"New","location":"","images":[],"dateTime":"2023-04-15T10:30:20Z","createdBy":"user2@gmail.com"}]
        String res = "[{\"id\":1,\"title\":\"Item Post 1\",\"description\":\"Item Post 1\",\"category\":\"Category\",\"condition\":\"New\",\"location\":\"\",\"images\":[],\"dateTime\":\"2023-04-15T10:30:20Z\",\"createdBy\":\"user2@gmail.com\"},{\"id\":2,\"title\":\"Item Post 2\",\"description\":\"Item Post 2\",\"category\":\"Category\",\"condition\":\"New\",\"location\":\"\",\"images\":[],\"dateTime\":\"2023-04-15T10:30:20Z\",\"createdBy\":\"user2@gmail.com\"}]";
        Assertions.assertThat(contentAsString).isEqualTo(res);

        String contentAsString2 = mvc.perform(get("/posts/user3@gmail.com").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //[{"id":3,"title":"Item Post 3","description":"Item Post 3","category":"Category","condition":"New","location":"","images":[],"dateTime":"2023-04-15T10:30:20Z","createdBy":"user3@gmail.com"}]
        String res2 = "[{\"id\":3,\"title\":\"Item Post 3\",\"description\":\"Item Post 3\",\"category\":\"Category\",\"condition\":\"New\",\"location\":\"\",\"images\":[],\"dateTime\":\"2023-04-15T10:30:20Z\",\"createdBy\":\"user3@gmail.com\"}]";
        Assertions.assertThat(contentAsString2).isEqualTo(res2);
    }
}