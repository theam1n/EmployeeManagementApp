package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionDto {

    private Long id;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double salary;

    private DepartmentDto department;
}
