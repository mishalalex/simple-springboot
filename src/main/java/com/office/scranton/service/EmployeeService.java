package com.office.scranton.service;

import com.office.scranton.dto.EmployeeDTO;
import com.office.scranton.entity.EmployeeEntity;
import com.office.scranton.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeEntity> employeeEntity = employeeRepository.findAll();
        return employeeEntity
                .stream()
                .map(eEntity -> modelMapper.map(eEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id){
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO){
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity eDto = employeeRepository.save(employeeEntity);
        return modelMapper.map(eDto, EmployeeDTO.class);
    }

    public boolean updateEmployee(Long id, EmployeeDTO employeeDTO){
        boolean exists = employeeRepository.existsById(id);
        if(!exists) return false;
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        employeeRepository.save(employeeEntity);
        return true;
    }

    public EmployeeDTO updateEmployeePartially(Long id, Map<String, Object> fieldsTobeUpdated){
        boolean exists = employeeRepository.existsById(id);
        if(!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        fieldsTobeUpdated.forEach((field, value) -> {
            Field fieldTobeUpdated = ReflectionUtils.getRequiredField(EmployeeEntity.class,field);
            fieldTobeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldTobeUpdated,employeeEntity,value);
            fieldTobeUpdated.setAccessible(false);
        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

    public boolean deleteEmployee(Long id){
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if(employeeEntity == null) return false;
        employeeRepository.delete(employeeEntity);
        return true;
    }
}
