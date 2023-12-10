package com.example.demo.service.impl;


import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.DepartmentRequest;
import com.example.demo.entity.Department;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    public void getDepartmentSuccessTest() {

        Long id = 1L;
        Department department = Department.builder().id(id).name("IT").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));

        DepartmentDto departmentDto = departmentService.getDepartment(id);

        assertThat(departmentDto).isNotNull();
        assertEquals(1,departmentDto.getId());
        assertEquals("IT",departmentDto.getName());

    }

    @Test
    public void getDepartmentErrorTest() {

        given(departmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> departmentService.getDepartment(anyLong()));

    }

    @Test
    public void saveDepartmentSuccessTest() {

        Long id = 1L;
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("IT");
        Department department = Department.builder().id(id).name("IT").build();

        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDto departmentDto = departmentService.saveDepartment(departmentRequest);

        assertThat(departmentDto).isNotNull();

    }

    @Test
    public void saveDepartmentErrorTest() {

        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("IT");

        when(departmentRepository.save(any(Department.class)))
                .thenThrow(new RuntimeException("Error during department save"));

        assertThrows(RuntimeException.class, () -> {
            departmentService.saveDepartment(departmentRequest);
        });
    }

    @Test
    public void getAllDepartmentsSuccessTest() {

        Department department1 = Department.builder().id(1L).name("IT").build();
        Department department2 = Department.builder().id(2L).name("Finance").build();

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1,department2));
        List<DepartmentDto> departmentDtos = departmentService.getAllDepartments();

        assertThat(departmentDtos.size()).isEqualTo(2);

    }

    @Test
    public void getAllDepartmentsErrorTest() {

        when(departmentRepository.findAll()).thenReturn(new ArrayList<>());

        List<DepartmentDto> departmentDtos = departmentService.getAllDepartments();

        assertThat(departmentDtos).isNotNull();
        assertThat(departmentDtos.size()).isEqualTo(0);

    }
    @Test
    public void editDepartmentSuccessTest() {

        Long id = 1L;
        DepartmentDto departmentRequest = new DepartmentDto();
        departmentRequest.setId(id);
        departmentRequest.setName("Finance");
        Department department = Department.builder().id(id).name("IT").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));
        department.setName(departmentRequest.getName());

        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDto departmentDto = departmentService.editDepartment(departmentRequest);

        assertThat(departmentDto).isNotNull();

    }

    @Test
    public void editDepartmentErrorTest() {

        Long id = 1L;
        DepartmentDto departmentRequest = new DepartmentDto();
        departmentRequest.setId(id);
        departmentRequest.setName("Finance");
        given(departmentRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> departmentService.editDepartment(departmentRequest));

    }

    @Test
    public void deleteDepartmentSuccessTest() {

        Long id = 1L;
        Department department = Department.builder().id(id).name("IT").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));
        willDoNothing().given(departmentRepository).deleteById(id);

        departmentService.deleteDepartment(id);

        verify(departmentRepository,times(1)).findById(id);
        verify(departmentRepository,times(1)).deleteById(id);

    }

    @Test
    public void deleteDepartmentErrorTest() {

        given(departmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> departmentService.deleteDepartment(anyLong()));

    }

}