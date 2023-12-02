package com.example.demo.service.impl;

import com.example.demo.dto.PositionRequest;
import com.example.demo.dto.PositionResponse;
import com.example.demo.entity.Position;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.repository.PositionRepository;
import com.example.demo.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    private final PositionMapper positionMapper;

    private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);
    @Override
    public PositionResponse savePosition(PositionRequest positionRequest) {
        logger.info("ActionLog.savePosition.start request: {}",positionRequest);

        var position = positionMapper.requestToEntity(positionRequest);
        var response = positionMapper.entityToResponse(positionRepository.save(position));

        logger.info("ActionLog.savePosition.end response: {}",response);

        return response;
    }

    @Override
    public PositionResponse getPosition(Long id) {
        logger.info("ActionLog.getPosition.start request: {}",id);

        Optional<Position> optionalPosition = Optional.ofNullable(positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position not found with id: " + id)));

        var position = optionalPosition.get();
        var response = positionMapper.entityToResponse(position);

        logger.info("ActionLog.getPosition.end response: {}",response);

        return response;
    }

    @Override
    public List<PositionResponse> getAllPositions() {
        logger.info("ActionLog.getAllPositions.start");

        var positions = positionRepository.findAll();
        var response = positions.stream()
                .map(positionMapper:: entityToResponse)
                .collect(Collectors.toList());

        logger.info("ActionLog.getAllPositions.end response: {}",response);

        return response;
    }

    @Override
    public PositionResponse editPosition(Position position) {
        logger.info("ActionLog.editPosition.start request: {}",position);

        Optional<Position> optionalPosition = Optional.ofNullable(positionRepository.findById(position.getId())
                .orElseThrow(() -> new NotFoundException("Position not found with id: " + position.getId())));

        var existingPosition = optionalPosition.get();

        existingPosition.setName(position.getName());

        var response = positionMapper
                .entityToResponse(positionRepository.save(existingPosition));

        logger.info("ActionLog.editPosition.end response: {}",response);

        return response;
    }

    @Override
    public void deletePosition(Long id) {

        logger.info("ActionLog.deletePosition.start request: {}",id);

        try {
            positionRepository.deleteById(id);
            logger.info("ActionLog.deletePosition.end");
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Error deleting position with id: {}. Position not found.", id);
        }

    }
}
