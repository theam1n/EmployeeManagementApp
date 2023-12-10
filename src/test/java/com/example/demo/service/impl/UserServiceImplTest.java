package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUserSuccessTest() {

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("amin03");
        userRequest.setPassword("123");

        User user = new User();
        user.setId(1L);
        user.setUsername("amin03");
        user.setPassword("123");

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(1L);
        expectedResponse.setUsername("amin03");

        when(userRepository.save(any(User.class))).thenReturn(user);


        UserResponse actualResponse = userService.saveUser(userRequest);

        assertThat(actualResponse).isNotNull();
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());

    }

    @Test
    void saveUserErrorTest() {

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Amin");
        userRequest.setUsername("amin03");
        userRequest.setPassword("123");

        when(userRepository.save(any(User.class)))
                .thenThrow(new RuntimeException("Error during user save"));

        assertThrows(RuntimeException.class, () -> {
            userService.saveUser(userRequest);
        });
    }

    @Test
    void loginSuccessTest() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("amin03");
        loginRequest.setPassword("123");

        User user = new User();
        user.setId(1L);
        user.setUsername("amin03");
        user.setPassword("123");

        String expectedToken = "testToken";

        when(userRepository.findByUsername("amin03")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(expectedToken);

        LoginResponse loginResponse = userService.login(loginRequest);

        assertEquals(expectedToken, loginResponse.getToken());

    }

    @Test
    void loginErrorTest() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("amin03");
        loginRequest.setPassword("123");

        when(userRepository.findByUsername("amin03")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.login(loginRequest);
        });

    }


}

