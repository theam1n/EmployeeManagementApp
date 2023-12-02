package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserResponse saveUser(UserRequest userRequest) {

        logger.info("ActionLog.saveUser.start request: {}",userRequest);

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        var user = userMapper.requestToEntity(userRequest);
        var response = userMapper.entityToResponse(userRepository.save(user));

        logger.info("ActionLog.saveUser.end response: {}", response);

        return response;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        logger.info("ActionLog.login.start request: {}",loginRequest);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        User user =  userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .build();

        logger.info("ActionLog.login.end response: {}",loginResponse);

        return loginResponse;
    }
}
