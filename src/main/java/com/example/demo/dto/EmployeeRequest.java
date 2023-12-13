package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String name;

    private String surname;

    @Email
    private String email;

    private DepartmentDto department;

    private PositionDto position;
}
