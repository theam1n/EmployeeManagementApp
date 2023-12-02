package com.example.demo.service;


import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse saveEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse getEmployee(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse editEmployee(Employee employee);

    void deleteEmployee(Long id);
}
