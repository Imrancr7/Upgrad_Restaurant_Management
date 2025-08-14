package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.dto.EmployeeInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.EmployeeLoginDTO;
import com.upgradProject.restaurantManagementSystem.entity.Employee;
import com.upgradProject.restaurantManagementSystem.service.interfaces.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        reset(employeeService);
    }

    @Test
    void login_ReturnsTokenWhenCredentialsAreValid() {
        EmployeeLoginDTO loginDTO = new EmployeeLoginDTO();
        String expectedToken = "token";
        when(employeeService.verifyEmployeeLogin(loginDTO)).thenReturn(expectedToken);

        String result = employeeController.login(loginDTO);

        assertThat(result).isEqualTo(expectedToken);
        verify(employeeService).verifyEmployeeLogin(loginDTO);
    }

    @Test
    void createEmployee_ReturnsCreatedEmployeeWithEncodedPassword() {
        EmployeeInputDTO input = EmployeeInputDTO.builder()
                .password("plainPassword").build();
        Employee created = new Employee();
        when(employeeService.createEmployee(any(EmployeeInputDTO.class))).thenReturn(created);

        ResponseEntity<Employee> result = employeeController.createEmployee(input);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(created);
        verify(employeeService).createEmployee(argThat(dto ->
                new BCryptPasswordEncoder(12).matches("plainPassword", dto.getPassword())
        ));
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> result = employeeController.getAllEmployees();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).containsExactlyElementsOf(employees);
        verify(employeeService).getAllEmployees();
    }

    @Test
    void getAllEmployees_ReturnsEmptyListWhenNoEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Employee>> result = employeeController.getAllEmployees();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEmpty();
        verify(employeeService).getAllEmployees();
    }

    @Test
    void getEmployeeById_ReturnsEmployeeWhenFound() {
        int id = 1;
        Employee employee = new Employee();
        when(employeeService.getEmployeeById(id)).thenReturn(employee);

        ResponseEntity<Employee> result = employeeController.getEmployeeById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(employee);
        verify(employeeService).getEmployeeById(id);
    }

    @Test
    void getEmployeeById_ReturnsNotFoundWhenEmployeeDoesNotExist() {
        int id = 99;
        when(employeeService.getEmployeeById(id)).thenReturn(null);

        ResponseEntity<Employee> result = employeeController.getEmployeeById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(result.getBody()).isNull();
        verify(employeeService).getEmployeeById(id);
    }

    @Test
    void updateEmployee_ReturnsUpdatedEmployee() {
        int id = 2;
        Employee updatedEmployee = new Employee();
        Employee updated = new Employee();
        when(employeeService.updateEmployee(id, updatedEmployee)).thenReturn(updated);

        ResponseEntity<Employee> result = employeeController.updateEmployee(id, updatedEmployee);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(updated);
        verify(employeeService).updateEmployee(id, updatedEmployee);
    }

    @Test
    void setEmployeePassword_ReturnsSuccessMessage() {
        int id = 3;
        String password = "newPassword";
        doNothing().when(employeeService).setEmployeePassword(id, password);

        ResponseEntity<String> result = employeeController.setEmployeePassword(id, password);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Password updated successfully");
        verify(employeeService).setEmployeePassword(id, password);
    }

    @Test
    void deleteEmployee_ReturnsSuccessMessage() {
        int id = 4;
        doNothing().when(employeeService).deleteEmployee(id);

        ResponseEntity<String> result = employeeController.deleteEmployee(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Employee deleted successfully");
        verify(employeeService).deleteEmployee(id);
    }
}
