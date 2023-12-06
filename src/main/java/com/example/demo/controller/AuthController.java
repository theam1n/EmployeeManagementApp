package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest request) {
        UserResponse response = userService.saveUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
