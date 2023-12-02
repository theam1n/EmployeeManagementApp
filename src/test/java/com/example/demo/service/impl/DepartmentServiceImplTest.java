package com.example.demo.service.impl;

import com.example.demo.dto.DepartmentResponse;
import com.example.demo.entity.Department;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    public void getDepartmentTest() {

        Long id = 1L;
        Department department = Department.builder().id(id).name("IT").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));

        Department departmentResponse = departmentService.getDepartment1(id);

        assertThat(departmentResponse).isNotNull();
        assertEquals(1,departmentResponse.getId());
        assertEquals("IT",departmentResponse.getName());



    }

}