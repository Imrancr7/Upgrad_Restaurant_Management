package com.upgradProject.restaurantManagementSystem.controller;


import com.upgradProject.restaurantManagementSystem.dto.EmployeeInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.EmployeeLoginDTO;
import com.upgradProject.restaurantManagementSystem.entity.Employee;
import com.upgradProject.restaurantManagementSystem.exceptions.GlobalExceptionHandler;
import com.upgradProject.restaurantManagementSystem.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public String login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        return employeeService.verifyEmployeeLogin(employeeLoginDTO);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeInputDTO employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Employee createdEmployee = employeeService.createEmployee(employee);
        logger.info("Employee created successfully");
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        Employee updated = employeeService.updateEmployee(id, updatedEmployee);
        logger.info("Employee Updated Successfully");
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/set-password/{id}")
    public ResponseEntity<String> setEmployeePassword(@PathVariable int id, @RequestParam String password) {
        employeeService.setEmployeePassword(id, password);
        logger.info("Password updated successfully");
        return ResponseEntity.ok("Password updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        logger.info("Employee deleted successfully");
        return ResponseEntity.ok("Employee deleted successfully");
    }


}
