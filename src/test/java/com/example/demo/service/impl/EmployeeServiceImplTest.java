package com.example.demo.service.impl;

import com.example.demo.dto.DepartmentRequest;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void getEmployeeSuccessTest() {

        Long id = 1L;
        Employee employee= Employee.builder().id(id).name("Amin").surname("Aliyev").build();
        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));

        EmployeeDto employeeDto = employeeService.getEmployee(id);

        assertThat(employeeDto).isNotNull();
        assertEquals(1,employeeDto.getId());
        assertEquals("Amin",employeeDto.getName());
        assertEquals("Aliyev",employeeDto.getSurname());

    }

    @Test
    public void getEmployeeErrorTest() {

        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> employeeService.getEmployee(anyLong()));

    }

    @Test
    public void saveEmployeeSuccessTest() {

        Long id = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Amin");
        employeeRequest.setSurname("Aliyev");
        Employee employee = Employee.builder().id(id).name("Amin").surname("Aliyev").build();

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto employeeDto = employeeService.saveEmployee(employeeRequest);

        assertThat(employeeDto).isNotNull();

    }

    @Test
    public void saveEmployeeErrorTest() {

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Amin");
        employeeRequest.setSurname("Aliyev");

        when(employeeRepository.save(any(Employee.class)))
                .thenThrow(new RuntimeException("Error during employee save"));

        assertThrows(RuntimeException.class, () -> {
            employeeService.saveEmployee(employeeRequest);
        });
    }

    @Test
    public void getAllEmployeesSuccessTest() {

        Employee employee1 = Employee.builder().id(1L).name("Amin").build();
        Employee employee2 = Employee.builder().id(2L).name("Ali").build();

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1,employee2));
        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();

        assertThat(employeeDtos.size()).isEqualTo(2);

    }

    @Test
    public void getAllEmployeesErrorTest() {

        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();

        assertThat(employeeDtos).isNotNull();
        assertThat(employeeDtos.size()).isEqualTo(0);

    }

    @Test
    public void editEmployeeSuccessTest() {

        Long id = 1L;
        EmployeeDto employeeRequest = new EmployeeDto();
        employeeRequest.setId(id);
        employeeRequest.setName("Amin");
        Employee employee = Employee.builder().id(id).name("Ali").build();
        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        employee.setName(employeeRequest.getName());

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto employeeDto = employeeService.editEmployee(employeeRequest);

        assertThat(employeeDto).isNotNull();

    }

    @Test
    public void editEmployeeErrorTest() {

        Long id = 1L;
        EmployeeDto employeeRequest = new EmployeeDto();
        employeeRequest.setId(id);
        employeeRequest.setName("Amin");
        given(employeeRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> employeeService.editEmployee(employeeRequest));

    }

    @Test
    public void deleteEmployeeSuccessTest() {

        Long id = 1L;
        Employee employee = Employee.builder().id(id).name("Amin").build();
        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        willDoNothing().given(employeeRepository).deleteById(id);

        employeeService.deleteEmployee(id);

        verify(employeeRepository,times(1)).findById(id);
        verify(employeeRepository,times(1)).deleteById(id);

    }

    @Test
    public void deleteEmployeeErrorTest() {

        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> employeeService.deleteEmployee(anyLong()));

    }


}