package org.parrot.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String email;
    private String password;
}
