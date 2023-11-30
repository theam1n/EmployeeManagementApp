package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;

public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    LoginResponse login(LoginRequest loginRequest);
}
