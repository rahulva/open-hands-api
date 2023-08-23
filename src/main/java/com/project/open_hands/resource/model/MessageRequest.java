package com.project.open_hands.resource.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String fromEmail;
    @NotNull
    @NotEmpty
    private String telephoneNo;
    @NotNull
    @NotEmpty
    private String messageText;
//    @NotNull
//    @NotEmpty
    private String requestTime;
    @NotNull
    @NotEmpty
    private String toEmail;
    @NotNull
    @NotEmpty
    private long postId;
}
