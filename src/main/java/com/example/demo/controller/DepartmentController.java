package com.example.demo.controller;

import com.example.demo.dto.DepartmentRequest;
import com.example.demo.dto.DepartmentResponse;
import com.example.demo.entity.Department;
import com.example.demo.service.impl.DepartmentServiceImpl;
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
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> saveDepartment(
            @RequestBody DepartmentRequest departmentRequest) {

        DepartmentResponse response = departmentService.saveDepartment(departmentRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartment(
            @PathVariable Long id) {

        DepartmentResponse response = departmentService.getDepartment(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments(){

        List<DepartmentResponse> response = departmentService.getAllDepartments();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<DepartmentResponse> editDepartment(
            @RequestBody Department department) {

        DepartmentResponse response = departmentService.editDepartment(department);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id){

        departmentService.deleteDepartment(id);

    }




}
