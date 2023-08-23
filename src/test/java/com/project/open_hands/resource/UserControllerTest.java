package com.project.open_hands.resource;

import com.project.open_hands.OpenHandsApiApplication;
import com.project.open_hands.entity.User;
import com.project.open_hands.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setup() {
        User u1 = new User("test@gmail.com", "Fname", "Lname", "test@pazz");
        repository.save(u1);

        Optional<User> fromDb = repository.findById("test@gmail.com");
        assertThat(fromDb.get()).isNotNull();
    }

    @AfterEach
    void after() {
        repository.deleteById("test@gmail.com");
    }

    @Test
    void getUsers() throws Exception {
        mvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@gmail.com"))
                .andExpect(jsonPath("$[0].firstName").value("Fname"))
                .andExpect(jsonPath("$[0].lastName").value("Lname"))
                .andExpect(jsonPath("$[0].password").value("test@pazz"));
    }

    @Test
    void createUser() throws Exception {
        var userObj = """
                {
                "email": "test2@gmail.com",
                "firstName" : "F2Name",
                "lastName" : "L2Name",
                "password" : "PAvdsw"
                }
                """;
        mvc.perform(post("/users").contentType("application/json").content(userObj))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test2@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("F2Name"))
                .andExpect(jsonPath("$.lastName").value("L2Name"))
                .andExpect(jsonPath("$.password").value("PAvdsw"));
        repository.deleteById("test2@gmail.com");
    }

    @Test
    void updateUser() throws Exception {
        var userObj = """
                {
                "email": "test@gmail.com",
                "firstName" : "F2.1Name",
                "lastName" : "L2.1Name",
                "password" : "PAvdsw"
                }
                """;
        mvc.perform(put("/users").contentType("application/json").content(userObj))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("F2.1Name"))
                .andExpect(jsonPath("$.lastName").value("L2.1Name"))
                .andExpect(jsonPath("$.password").value("PAvdsw"));
    }

    @Test
    void getUser() throws Exception {
        mvc.perform(get("/users/test@gmail.com").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Fname"))
                .andExpect(jsonPath("$.lastName").value("Lname"))
                .andExpect(jsonPath("$.password").value("test@pazz"));
    }

    @Test
    void loginUnauthorized() throws Exception {
        mvc.perform(post("/users/login").content("email=test@gmail.com&password=PAvdsw").contentType("application/x-www-form-urlencoded"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login() throws Exception {
        mvc.perform(post("/users/login").content("email=test@gmail.com&password=test@pazz").contentType("application/x-www-form-urlencoded"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Fname"))
                .andExpect(jsonPath("$.lastName").value("Lname"))
                .andExpect(jsonPath("$.password").value("test@pazz"));
    }

    @Test
    void loginUnsupportedMediaType() throws Exception {
        mvc.perform(post("/users/login").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void loginBadRequest() throws Exception {
        mvc.perform(post("/users/login").contentType("application/x-www-form-urlencoded"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUser() {

    }
}