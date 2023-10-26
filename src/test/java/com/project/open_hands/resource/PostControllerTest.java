package com.project.open_hands.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.open_hands.TestDemoApplication;
import com.project.open_hands.entity.Post;
import com.project.open_hands.repository.PostRepository;
import com.project.open_hands.repository.UserRepository;
import org.assertj.core.groups.Tuple;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestDemoApplication.class)
@ContextConfiguration(initializers = {PostControllerTest.Initializer.class})
@AutoConfigureMockMvc
class PostControllerTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.jpa.hibernate.ddl-auto=update"
            ).applyTo(applicationContext);
        }
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

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
        post1.setDateTime(LocalDateTime.now(clock));
        post1.setCreatedBy("user2@gmail.com");

        try {
            mvc.perform(post("/posts").contentType("application/json").content(mapper.writeValueAsString(post1)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Post post2 = new Post();
        post2.setTitle("Item Post 5");
        post2.setDescription("Item Post 2");
        post2.setCategory("Category");
        post2.setCondition("New");
        post2.setImages(List.of());
        post2.setLocation("");
        post2.setDateTime(LocalDateTime.now(clock));
        post2.setCreatedBy("user2@gmail.com");

        try {
            mvc.perform(post("/posts").contentType("application/json").content(mapper.writeValueAsString(post2)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Post post3 = new Post();
        post3.setTitle("Item Post 6");
        post3.setDescription("Item Post 3");
        post3.setCategory("Category");
        post3.setCondition("New");
        post3.setImages(List.of());
        post3.setLocation("");
        post3.setDateTime(LocalDateTime.now(clock));
        post3.setCreatedBy("user3@gmail.com");

        try {
            mvc.perform(post("/posts").contentType("application/json").content(mapper.writeValueAsString(post3)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void after() {
        try {
            mvc.perform(delete("/posts").contentType("application/json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create() throws Exception {
//                "images" : ["test_img","test_img2"],
        var userObj = """
                {
                "title": "Razer balde",
                "description" : "This razer blade is brand new and unused. I bouth it and never get chance to use it.",
                "category" : "Self care",
                "condition" : "Facial products",
                "location" : "Frankfurt",
                "dateTime" : "2023-10-11 20:36:52",
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

        postRepository.delete(postRepository.findByTitle("Razer balde"));
    }

    @Test
    void getAllPost() throws Exception {

        String contentAsString = mvc.perform(get("/posts").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //noinspection unchecked
        List<Post> list = mapper.readValue(contentAsString, List.class);
        assertThat(list).hasSize(3);
    }

    @Test
    void getAllPostForUser() throws Exception {
        String contentAsString = mvc.perform(get("/posts/user2@gmail.com").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //noinspection unchecked
        List<Post> list = mapper.readValue(contentAsString, List.class);
        assertThat(list).hasSize(2);
/*        assertThat(list)
                .extracting(Post::getTitle, Post::getDescription, Post::getCategory, Post::getCondition, Post::getCreatedBy, Post::getCondition, Post::getLocation, Post::getImages, Post::getDateTime, Post::getCreatedBy)
                .contains(
                        Tuple.tuple("Item Post 4", "Item Post 1", "Category", "New", "", List.of(), LocalDateTime.parse("2023-04-15 10:30:20.000000"), "user2@gmail.com"),
                        Tuple.tuple("Item Post 5", "Item Post 2", "Category", "New", "", List.of(), LocalDateTime.parse("2023-04-15 10:30:20.000000"), "user2@gmail.com")
                );*/

        String contentAsString2 = mvc.perform(get("/posts/user3@gmail.com").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //noinspection unchecked
        List<Post> list2 = mapper.readValue(contentAsString2, List.class);
        assertThat(list2).hasSize(1);
    }
}