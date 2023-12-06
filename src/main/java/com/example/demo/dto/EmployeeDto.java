package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private DepartmentDto department;

    private PositionDto position;
}