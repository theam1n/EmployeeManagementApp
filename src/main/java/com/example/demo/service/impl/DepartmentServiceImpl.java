package com.example.demo.service.impl;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.DepartmentRequest;
import com.example.demo.entity.Department;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public DepartmentDto saveDepartment(DepartmentRequest departmentRequest) {

        logger.info("ActionLog.saveDepartment.start request: {}",departmentRequest);

        var department = departmentMapper.requestToEntity(departmentRequest);
        var response = departmentMapper.entityToResponse(departmentRepository.save(department));

        logger.info("ActionLog.saveDepartment.end response: {}",response);

        return response;
    }

    @Override
    public DepartmentDto getDepartment(Long id) {

        logger.info("ActionLog.getDepartment.start request: {}",id);

        Optional<Department> optionalDepartment = Optional.ofNullable(departmentRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Department not found with id: " + id)));

        var department = optionalDepartment.get();
        var response = departmentMapper.entityToResponse(department);

        logger.info("ActionLog.getDepartment.end response: {}",response);

        return response;
    }


    @Override
    public List<DepartmentDto> getAllDepartments() {

        logger.info("ActionLog.getAllDepartments.start");

        var departments = departmentRepository.findAll();
        var response = departments.stream()
                .map(departmentMapper:: entityToResponse)
                .collect(Collectors.toList());

        logger.info("ActionLog.getAllDepartments.end response: {}",response);

        return response;
    }

    @Override
    public DepartmentDto editDepartment(DepartmentDto department) {

        logger.info("ActionLog.editDepartment.start request: {}",department);

        Optional<Department> optionalDepartment = Optional.ofNullable(departmentRepository.findById(department.getId())
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + department.getId())));

        var existingDepartment = optionalDepartment.get();

        Optional.ofNullable(department.getName()).ifPresent(existingDepartment::setName);

        var response = departmentMapper
                .entityToResponse(departmentRepository.save(existingDepartment));

        logger.info("ActionLog.editDepartment.end response: {}",response);

        return response;
    }

    @Override
    public void deleteDepartment(Long id) {

        logger.info("ActionLog.deleteDepartment.start request: {}",id);

        try {
            departmentRepository.deleteById(id);
            logger.info("ActionLog.deleteDepartment.end");
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Error deleting department with id: {}. Department not found.", id);
        }

    }
}
