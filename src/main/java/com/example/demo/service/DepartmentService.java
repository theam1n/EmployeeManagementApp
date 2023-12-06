package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.DepartmentRequest;

import java.util.List;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentRequest departmentRequest);

    DepartmentDto getDepartment(Long id);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto editDepartment(DepartmentDto department);

    void deleteDepartment(Long id);

}
