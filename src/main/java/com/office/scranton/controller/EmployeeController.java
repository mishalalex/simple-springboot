package com.office.scranton.controller;

import com.office.scranton.dto.EmployeeDTO;
import com.office.scranton.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employee) {
        return new ResponseEntity<>(employeeService.createNewEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        if(employeeService.getEmployeeById(employeeId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<HttpStatus> updateEmployeeById(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDto) {
        if(!employeeService.updateEmployee(employeeId, employeeDto)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartiallyEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(employeeService.updateEmployeePartially(employeeId, updates), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable Long employeeId) {
        if(!employeeService.deleteEmployee(employeeId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
