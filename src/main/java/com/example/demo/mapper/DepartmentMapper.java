package com.example.demo.mapper;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.DepartmentRequest;
import com.example.demo.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

    public static final DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    @Mapping(target = "id", ignore = true)
    Department requestToEntity(DepartmentRequest departmentRequest);

    DepartmentDto entityToResponse(Department department);

    Department dtoToEntity(DepartmentDto departmentDto);

}
