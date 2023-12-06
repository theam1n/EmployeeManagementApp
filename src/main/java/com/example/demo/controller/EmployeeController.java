package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.entity.Employee;
import com.example.demo.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> saveResponse(
            @RequestBody EmployeeRequest employeeRequest) {

        EmployeeDto response = employeeService.saveEmployee(employeeRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getResponse(
            @PathVariable Long id) {

        EmployeeDto response = employeeService.getEmployee(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){

        List<EmployeeDto> response = employeeService.getAllEmployees();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> editEmployee(
            @RequestBody EmployeeDto employee) {

        EmployeeDto response = employeeService.editEmployee(employee);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id){

        employeeService.deleteEmployee(id);

    }
}
