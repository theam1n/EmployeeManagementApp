package com.example.demo.service;


import com.example.demo.dto.PositionRequest;
import com.example.demo.dto.PositionResponse;
import com.example.demo.entity.Position;

import java.util.List;

public interface PositionService {

    PositionResponse savePosition(PositionRequest positionRequest);

    PositionResponse getPosition(Long id);

    List<PositionResponse> getAllPositions();

    PositionResponse editPosition(Position position);

    void deletePosition(Long id);
}
