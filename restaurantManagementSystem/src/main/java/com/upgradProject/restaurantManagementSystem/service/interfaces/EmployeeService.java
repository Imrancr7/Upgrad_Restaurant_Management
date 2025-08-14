package com.upgradProject.restaurantManagementSystem.service.interfaces;

import com.upgradProject.restaurantManagementSystem.dto.EmployeeInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.EmployeeLoginDTO;
import com.upgradProject.restaurantManagementSystem.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeInputDTO employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    Employee updateEmployee(int id, Employee updatedEmployee);
    String verifyEmployeeLogin(EmployeeLoginDTO employeeLoginDTO);
    void deleteEmployee(int id);

    void setEmployeePassword(int id, String password);
}