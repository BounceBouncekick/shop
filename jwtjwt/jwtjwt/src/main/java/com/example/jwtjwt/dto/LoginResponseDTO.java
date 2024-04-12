package com.example.jwtjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String token;

    // Constructor and Getter

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}