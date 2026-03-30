package com.park.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.park.auth.dto.LoginRequest;
import com.park.auth.service.AuthService;
import com.park.common.dto.UserDto;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
    	System.out.println("User name  ->"+request.getUserName());
        return authService.login(request);
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserDto userDto) {
    	return authService.createUser(userDto);
    }
    
}
