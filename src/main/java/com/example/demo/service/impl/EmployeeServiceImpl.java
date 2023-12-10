package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.entity.Employee;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public EmployeeDto saveEmployee(EmployeeRequest employeeRequest) {

        logger.info("ActionLog.saveEmployee.start request: {}",employeeRequest);

        var employee = EmployeeMapper.INSTANCE.requestToEntity(employeeRequest);
        var response = EmployeeMapper.INSTANCE.entityToResponse(employeeRepository.save(employee));

        logger.info("ActionLog.saveEmployee.end response: {}",response);

        return response;
    }

    @Override
    public EmployeeDto getEmployee(Long id) {

        logger.info("ActionLog.getEmployee.start request: {}",id);

        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id)));

        var employee = optionalEmployee.get();
        var response = EmployeeMapper.INSTANCE.entityToResponse(employee);

        logger.info("ActionLog.getEmployee.end response: {}",response);

        return response;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        logger.info("ActionLog.getAllEmployees.start");

        var employees = employeeRepository.findAll();
        var response = employees.stream()
                .map(EmployeeMapper.INSTANCE:: entityToResponse)
                .collect(Collectors.toList());

        logger.info("ActionLog.getAllEmployees.end response: {}",response);

        return response;
    }

    @Override
    public EmployeeDto editEmployee(EmployeeDto employee) {

        logger.info("ActionLog.editEmployee.start request: {}",employee);

        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employee.getId())));

        var existingEmployee = optionalEmployee.get();

        Optional.ofNullable(employee.getName()).ifPresent(existingEmployee::setName);
        Optional.ofNullable(employee.getSurname()).ifPresent(existingEmployee::setSurname);
        Optional.ofNullable(employee.getEmail()).ifPresent(existingEmployee::setEmail);
        Optional.ofNullable(employee.getDepartment())
                .map(DepartmentMapper.INSTANCE::dtoToEntity)
                .ifPresent(existingEmployee::setDepartment);
        Optional.ofNullable(employee.getPosition())
                .map(PositionMapper.INSTANCE::dtoToEntity)
                .ifPresent(existingEmployee::setPosition);


        var response = EmployeeMapper.INSTANCE
                .entityToResponse(employeeRepository.save(existingEmployee));

        logger.info("ActionLog.editEmployee.end response: {}",response);

        return response;
    }

    @Override
    public void deleteEmployee(Long id) {

        logger.info("ActionLog.deleteEmployee.start request: {}",id);

        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id : " + id));

        employeeRepository.deleteById(id);

        logger.info("ActionLog.deleteEmployee.end");

    }
}
