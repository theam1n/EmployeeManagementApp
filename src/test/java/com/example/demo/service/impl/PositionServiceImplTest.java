package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.PositionDto;
import com.example.demo.dto.PositionRequest;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Position;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.PositionRepository;
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
class PositionServiceImplTest {


    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionServiceImpl positionService;

    @Test
    public void getPositionSuccessTest() {

        Long id = 1L;
        Position position = Position.builder().id(id).name("Web Developer").salary(1200.0).build();
        given(positionRepository.findById(id)).willReturn(Optional.of(position));

        PositionDto positionDto = positionService.getPosition(id);

        assertThat(positionDto).isNotNull();
        assertEquals(1,positionDto.getId());
        assertEquals("Web Developer",positionDto.getName());
        assertEquals(1200.0,positionDto.getSalary());

    }

    @Test
    public void getPositionErrorTest() {

        given(positionRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> positionService.getPosition(anyLong()));

    }

    @Test
    public void savePositionSuccessTest() {

        Long id = 1L;
        PositionRequest positionRequest = new PositionRequest();
        positionRequest.setName("Web Developer");
        positionRequest.setSalary(1200.0);
        Position position = Position.builder().id(id).name("Web Developer").salary(1200.0).build();

        when(positionRepository.save(any(Position.class))).thenReturn(position);
        PositionDto positionDto = positionService.savePosition(positionRequest);

        assertThat(positionDto).isNotNull();

    }

    @Test
    public void savePositionErrorTest() {

        PositionRequest positionRequest = new PositionRequest();
        positionRequest.setName("Web Developer");
        positionRequest.setSalary(1200.0);

        when(positionRepository.save(any(Position.class)))
                .thenThrow(new RuntimeException("Error during position save"));

        assertThrows(RuntimeException.class, () -> {
            positionService.savePosition(positionRequest);
        });
    }

    @Test
    public void getAllPositionsSuccessTest() {

        Position position1 = Position.builder().id(1L).name("Back End Developer").build();
        Position position2 = Position.builder().id(2L).name("Front End Developer").build();

        when(positionRepository.findAll()).thenReturn(Arrays.asList(position1,position2));
        List<PositionDto> positionDtos = positionService.getAllPositions();

        assertThat(positionDtos.size()).isEqualTo(2);

    }

    @Test
    public void getAllPositionsErrorTest() {

        when(positionRepository.findAll()).thenReturn(new ArrayList<>());

        List<PositionDto> positionDtos = positionService.getAllPositions();

        assertThat(positionDtos).isNotNull();
        assertThat(positionDtos.size()).isEqualTo(0);

    }
    @Test
    public void editPositionSuccessTest() {

        Long id = 1L;
        PositionDto positionRequest = new PositionDto();
        positionRequest.setId(id);
        positionRequest.setName("Back End Developer");
        positionRequest.setSalary(1500.0);
        Position position = Position.builder().id(id).name("Front End Developer").salary(1300.0).build();
        given(positionRepository.findById(id)).willReturn(Optional.of(position));
        position.setName(positionRequest.getName());
        position.setSalary(positionRequest.getSalary());

        when(positionRepository.save(any(Position.class))).thenReturn(position);
        PositionDto positionDto = positionService.editPosition(positionRequest);

        assertThat(positionDto).isNotNull();

    }

    @Test
    public void editPositionErrorTest() {

        Long id = 1L;
        PositionDto positionRequest = new PositionDto();
        positionRequest.setId(id);
        positionRequest.setName("Web Developer");
        positionRequest.setSalary(1200.0);
        given(positionRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> positionService.editPosition(positionRequest));

    }

    @Test
    public void deletePositionSuccessTest() {

        Long id = 1L;
        Position position = Position.builder().id(id).name("Web Developer").salary(1200.0).build();
        given(positionRepository.findById(id)).willReturn(Optional.of(position));
        willDoNothing().given(positionRepository).deleteById(id);

        positionService.deletePosition(id);

        verify(positionRepository,times(1)).findById(id);
        verify(positionRepository,times(1)).deleteById(id);

    }

    @Test
    public void deletePositionErrorTest() {

        given(positionRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> positionService.deletePosition(anyLong()));

    }



}