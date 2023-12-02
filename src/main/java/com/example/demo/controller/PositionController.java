package com.example.demo.controller;

import com.example.demo.dto.PositionRequest;
import com.example.demo.dto.PositionResponse;
import com.example.demo.entity.Position;
import com.example.demo.service.impl.PositionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionServiceImpl positionService;

    @PostMapping
    public ResponseEntity<PositionResponse> savePosition(
            @RequestBody PositionRequest positionRequest) {

        PositionResponse response = positionService.savePosition(positionRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPosition(
            @PathVariable Long id) {

        PositionResponse response = positionService.getPosition(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllPositions(){

        List<PositionResponse> response = positionService.getAllPositions();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PositionResponse> editPosition(
            @RequestBody Position position) {

        PositionResponse response = positionService.editPosition(position);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable Long id){

        positionService.deletePosition(id);

    }
}
