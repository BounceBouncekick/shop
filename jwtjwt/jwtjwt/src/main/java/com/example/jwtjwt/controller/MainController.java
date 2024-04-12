package com.example.jwtjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP() {

        return "main Controller";
    }

    @GetMapping("/jwt-service/health-check")
    public String status(){
        return "It`s Working in User Service";
    }
}