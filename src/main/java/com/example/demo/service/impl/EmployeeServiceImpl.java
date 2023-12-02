package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {

        logger.info("ActionLog.saveEmployee.start request: {}",employeeRequest);

        var employee = employeeMapper.requestToEntity(employeeRequest);
        var response = employeeMapper.entityToResponse(employeeRepository.save(employee));

        logger.info("ActionLog.saveEmployee.end response: {}",response);

        return response;
    }

    @Override
    public EmployeeResponse getEmployee(Long id) {

        logger.info("ActionLog.getEmployee.start request: {}",id);

        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id)));

        var employee = optionalEmployee.get();
        var response = employeeMapper.entityToResponse(employee);

        logger.info("ActionLog.getEmployee.end response: {}",response);

        return response;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        logger.info("ActionLog.getAllEmployees.start");

        var employees = employeeRepository.findAll();
        var response = employees.stream()
                .map(employeeMapper:: entityToResponse)
                .collect(Collectors.toList());

        logger.info("ActionLog.getAllEmployees.end response: {}",response);

        return response;
    }

    @Override
    public EmployeeResponse editEmployee(Employee employee) {

        logger.info("ActionLog.editEmployee.start request: {}",employee);

        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employee.getId())));

        var existingEmployee = optionalEmployee.get();

        existingEmployee.setName(employee.getName());

        var response = employeeMapper
                .entityToResponse(employeeRepository.save(existingEmployee));

        logger.info("ActionLog.editEmployee.end response: {}",response);

        return response;
    }

    @Override
    public void deleteEmployee(Long id) {

        logger.info("ActionLog.deleteEmployee.start request: {}",id);

        try {
            employeeRepository.deleteById(id);
            logger.info("ActionLog.deleteEmployee.end");
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Error deleting employee with id: {}. Employee not found.", id);
        }

    }
}
