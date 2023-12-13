package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;

    private String surname;

    @Email
    private String email;

    private String username;

    @Pattern(regexp = "^.{8,20}$", message = "Password length must be between 8-20 characters.")
    private String password;

    private Set<RoleDto> roles;
}
