package org.parrot.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterUserRequest {
    private String userName;
    private String email;
    private String password;
    private LocalDateTime dateCreated = LocalDateTime.now();
}
