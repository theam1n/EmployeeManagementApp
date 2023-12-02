package com.example.demo.service;

import com.example.demo.dto.DepartmentRequest;
import com.example.demo.dto.DepartmentResponse;
import com.example.demo.entity.Department;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse saveDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse getDepartment(Long id);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse editDepartment(Department department);

    void deleteDepartment(Long id);

}
