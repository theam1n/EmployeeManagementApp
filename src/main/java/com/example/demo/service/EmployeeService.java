package com.example.demo.service;


import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeRequest;

import java.util.List;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeRequest employeeRequest);

    EmployeeDto getEmployee(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto editEmployee(EmployeeDto employee);

    void deleteEmployee(Long id);
}
