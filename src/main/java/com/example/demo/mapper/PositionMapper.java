package com.example.demo.mapper;

import com.example.demo.dto.PositionDto;
import com.example.demo.dto.PositionRequest;
import com.example.demo.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PositionMapper {

    public static final PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);
    @Mapping(target = "id", ignore = true)
    Position requestToEntity(PositionRequest positionRequest);

    PositionDto entityToResponse(Position position);

    Position dtoToEntity(PositionDto positionDto);
}
