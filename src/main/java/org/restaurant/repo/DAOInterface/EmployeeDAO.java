package org.restaurant.repo.DAOInterface;

import org.restaurant.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee addEmployee(Employee employee);
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(int employeeId);

    Employee getEmployeeByEmail(String email);
}

