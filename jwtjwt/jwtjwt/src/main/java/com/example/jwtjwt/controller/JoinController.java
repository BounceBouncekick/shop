package com.example.jwtjwt.controller;

import com.example.jwtjwt.dto.JoinDTO;
import com.example.jwtjwt.jwt.JWTUtil;
import com.example.jwtjwt.sevice.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/jwt-service/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }


    @GetMapping("/jwt-service/{name}/exists")
    public ResponseEntity<Boolean> checkNameDuplicate(@PathVariable String name){
        return ResponseEntity.ok(joinService.checkNameDuplicate(name));
    }
}