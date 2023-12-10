package com.example.demo.mapper;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    public static final EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    @Mapping(target = "id", ignore = true)
    Employee requestToEntity(EmployeeRequest employeeRequest);

    EmployeeDto entityToResponse(Employee employee);
}
