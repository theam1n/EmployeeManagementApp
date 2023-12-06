package com.example.demo.controller;

import com.example.demo.dto.PositionDto;
import com.example.demo.dto.PositionRequest;
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
    public ResponseEntity<PositionDto> savePosition(
            @RequestBody PositionRequest positionRequest) {

        PositionDto response = positionService.savePosition(positionRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> getPosition(
            @PathVariable Long id) {

        PositionDto response = positionService.getPosition(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PositionDto>> getAllPositions(){

        List<PositionDto> response = positionService.getAllPositions();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PositionDto> editPosition(
            @RequestBody PositionDto position) {

        PositionDto response = positionService.editPosition(position);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable Long id){

        positionService.deletePosition(id);

    }
}
