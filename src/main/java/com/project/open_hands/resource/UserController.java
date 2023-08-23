package com.project.open_hands.resource;

import com.project.open_hands.entity.User;
import com.project.open_hands.repository.UserRepository;
import com.project.open_hands.resource.model.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public static final String UNABLE_TO_FIND_USER = "Unable to find user";
    private final UserRepository repository;

    @GetMapping
    public Collection<User> getUsers() {
        return repository.findAll();
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        Optional<User> oldUser = repository.findById(user.getEmail());
        if (oldUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist!!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> byId = repository.findById(user.getEmail());
        if (byId.isPresent()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(repository.save(user));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, UNABLE_TO_FIND_USER);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        Optional<User> byId = repository.findById(email);
        return ResponseEntity.ok(byId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UNABLE_TO_FIND_USER)));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<User> login(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank() ||
                request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valid Email and password must be provided");
        }
        Optional<User> byId = repository.findById(request.getEmail());
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.filter(user -> request.getPassword().equals(user.getPassword()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username password does not match")));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, UNABLE_TO_FIND_USER);

    }


    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        repository.deleteById(email);
        return ResponseEntity.ok().build();
    }
}