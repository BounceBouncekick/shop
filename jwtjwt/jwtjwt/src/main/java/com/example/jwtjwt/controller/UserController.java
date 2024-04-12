package com.example.jwtjwt.controller;

import com.example.jwtjwt.dto.JoinDTO;
import com.example.jwtjwt.dto.LoginResponseDTO;
import com.example.jwtjwt.sevice.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @PostMapping("/jwt-service/login")
//    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
//        String token = authenticationService.authenticateUser(username, password);
//
//        if (token != null) {
//            return token;
//        } else {
//            return null;
//        }
//    }

    @PostMapping("/jwt-service/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        String token = authenticationService.authenticateUser(username, password);

        if (token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(headers).body("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }
    }
}