package com.office.scranton.controller;

import com.office.scranton.dto.EmployeeDTO;
import com.office.scranton.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    public final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.createNewEmployee(employee);
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping(path = "/{employeeId}")
    public boolean updateEmployeeById(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDto) {
        return employeeService.updateEmployee(employeeId, employeeDto);
    }

    @PatchMapping(path = "/{employeeId}")
    public EmployeeDTO updatePartiallyEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates) {
        return employeeService.updateEmployeePartially(employeeId, updates);
    }

    @DeleteMapping(path = "/{employeeId}")
    public boolean deleteEmployeeById(@PathVariable Long employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
