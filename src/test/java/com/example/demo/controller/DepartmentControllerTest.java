package com.example.demo.controller;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.DepartmentRequest;
import com.example.demo.service.impl.DepartmentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentServiceImpl departmentService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveDepartmentSuccessTest() throws Exception{

        DepartmentRequest request = new DepartmentRequest("IT");
        DepartmentDto response = new DepartmentDto(1L,"IT");

        when(departmentService.saveDepartment(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/department")
                .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                        new SimpleGrantedAuthority("User")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("IT"));

    }

    @Test
    void getDepartmentSuccessTest() throws Exception{

        Long id = 1L;
        DepartmentDto response = new DepartmentDto(1L,"IT");

        when(departmentService.getDepartment(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/department/{id}",id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("IT"));

    }

    @Test
    void getAllDepartmentsSuccessTest() throws Exception{

        DepartmentDto response1 = new DepartmentDto(1L,"IT");
        DepartmentDto response2 = new DepartmentDto(2L,"Finance");

        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(response1,response2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/department")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    void editDepartmentSuccessTest() throws Exception{

        DepartmentDto request = new DepartmentDto(1L,"IT");
        DepartmentDto response = new DepartmentDto(1L,"IT");

        when(departmentService.editDepartment(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/department")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("IT"));

    }

    @Test
    void deleteDepartmentSuccessTest() throws Exception{

        Long id = 1L;
        willDoNothing().given(departmentService).deleteDepartment(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/department/{id}",id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"))))
                .andExpect(status().isOk())
                .andDo(print());

    }

}