package com.example.demo.controller;

import com.example.demo.dto.PositionDto;
import com.example.demo.dto.PositionRequest;
import com.example.demo.service.impl.PositionServiceImpl;
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
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionServiceImpl positionService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void savePositionSuccessTest() throws Exception{

        PositionRequest request = new PositionRequest();
        request.setName("Web Developer");
        request.setSalary(1200.0);
        PositionDto response = new PositionDto();
        response.setId(1L);
        response.setName("Web Developer");
        response.setSalary(1200.0);

        when(positionService.savePosition(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Web Developer"))
                .andExpect(jsonPath("$.salary").value(1200.0));

    }

    @Test
    void getPositionSuccessTest() throws Exception{

        Long id = 1L;
        PositionDto response = new PositionDto();
        response.setId(1L);
        response.setName("Web Developer");
        response.setSalary(1200.0);

        when(positionService.getPosition(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/position/{id}",id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Web Developer"))
                .andExpect(jsonPath("$.salary").value(1200.0));

    }

    @Test
    void getAllPositionsSuccessTest() throws Exception{

        PositionDto response1 = new PositionDto();
        response1.setId(1L);
        response1.setName("Back End Developer");
        response1.setSalary(1200.0);
        PositionDto response2 = new PositionDto();
        response2.setId(2L);
        response2.setName("Front End Developer");
        response2.setSalary(1100.0);

        when(positionService.getAllPositions()).thenReturn(Arrays.asList(response1,response2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/position")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    void editPositionSuccessTest() throws Exception{

        PositionDto request = new PositionDto();
        request.setId(1L);
        request.setName("Web Developer");
        request.setSalary(1200.0);
        PositionDto response = new PositionDto();
        response.setId(1L);
        response.setName("Web Developer");
        response.setSalary(1200.0);

        when(positionService.editPosition(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/position")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"),
                                new SimpleGrantedAuthority("User")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Web Developer"))
                .andExpect(jsonPath("$.salary").value(1200.0));

    }

    @Test
    void deletePositionSuccessTest() throws Exception{

        Long id = 1L;
        willDoNothing().given(positionService).deletePosition(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/position/{id}",id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin"))))
                .andExpect(status().isOk())
                .andDo(print());

    }

}