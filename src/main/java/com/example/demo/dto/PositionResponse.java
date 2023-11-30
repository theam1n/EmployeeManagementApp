package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponse {

    private Long id;

    private String name;

    private double salary;

    private DepartmentResponse departmentResponse;
}
