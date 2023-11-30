package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private DepartmentResponse departmentResponse;

    private PositionResponse positionResponse;
}
