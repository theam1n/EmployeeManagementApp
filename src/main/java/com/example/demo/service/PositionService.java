package com.example.demo.service;


import com.example.demo.dto.PositionDto;
import com.example.demo.dto.PositionRequest;

import java.util.List;

public interface PositionService {

    PositionDto savePosition(PositionRequest positionRequest);

    PositionDto getPosition(Long id);

    List<PositionDto> getAllPositions();

    PositionDto editPosition(PositionDto position);

    void deletePosition(Long id);
}
