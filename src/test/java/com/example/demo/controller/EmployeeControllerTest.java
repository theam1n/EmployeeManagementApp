package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.service.impl.EmployeeServiceImpl;
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
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveEmployeeSuccessTest() throws Exception{

        EmployeeRequest request = new EmployeeRequest();
        request.setName("Amin");
        request.setSurname("Aliyev");
        EmployeeDto response = new EmployeeDto();
        response.setId(1L);
        response.setName("Amin");
        response.setSurname("Aliyev");

        when(employeeService.saveEmployee(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employee")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Amin"))
                .andExpect(jsonPath("$.surname").value("Aliyev"));

    }

    @Test
    void getEmployeeSuccessTest() throws Exception{

        Long id = 1L;
        EmployeeDto response = new EmployeeDto();
        response.setId(1L);
        response.setName("Amin");
        response.setSurname("Aliyev");

        when(employeeService.getEmployee(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee/{id}",id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Amin"))
                .andExpect(jsonPath("$.surname").value("Aliyev"));

    }

    @Test
    void getAllEmployeesSuccessTest() throws Exception{

        EmployeeDto response1 = new EmployeeDto();
        response1.setId(1L);
        response1.setName("Amin");
        response1.setSurname("Aliyev");
        EmployeeDto response2 = new EmployeeDto();
        response2.setId(2L);
        response2.setName("Ali");
        response2.setSurname("Aliyev");

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(response1,response2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    void editEmployeeSuccessTest() throws Exception{

        EmployeeDto request = new EmployeeDto();
        request.setId(1L);
        request.setName("Amin");
        request.setSurname("Aliyev");
        EmployeeDto response = new EmployeeDto();
        response.setId(1L);
        response.setName("Amin");
        response.setSurname("Aliyev");

        when(employeeService.editEmployee(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/employee")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Amin"))
                .andExpect(jsonPath("$.surname").value("Aliyev"));

    }

    @Test
    void deleteEmployeeSuccessTest() throws Exception{

        Long id = 1L;
        willDoNothing().given(employeeService).deleteEmployee(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employee/{id}",id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"))))
                .andExpect(status().isOk())
                .andDo(print());

    }

}