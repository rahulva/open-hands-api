package com.project.open_hands.resource.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
