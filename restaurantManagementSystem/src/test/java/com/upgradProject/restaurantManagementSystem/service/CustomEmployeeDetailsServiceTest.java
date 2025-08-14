package com.upgradProject.restaurantManagementSystem.service;

import com.upgradProject.restaurantManagementSystem.dto.UserPrincipal;
import com.upgradProject.restaurantManagementSystem.entity.Employee;
import com.upgradProject.restaurantManagementSystem.exceptions.EmployeeNotFoundException;
import com.upgradProject.restaurantManagementSystem.repo.EmployeeRepository;
import com.upgradProject.restaurantManagementSystem.service.impls.CustomEmployeeDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomEmployeeDetailsServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CustomEmployeeDetailsService customEmployeeDetailsService;

    @BeforeEach
    void setUp() {
        reset(employeeRepository);
    }

    @Test
    void loadUserByUsername_ReturnsUserPrincipalWhenEmployeeExists() {
        String email = "employee@example.com";
        Employee employee = new Employee();
        employee.setEmail(email);

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(employee));

        UserDetails result = customEmployeeDetailsService.loadUserByUsername(email);

        assertThat(result).isInstanceOf(UserPrincipal.class);
        verify(employeeRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsername_ThrowsEmployeeNotFoundExceptionWhenEmployeeDoesNotExist() {
        String email = "notfound@example.com";
        when(employeeRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customEmployeeDetailsService.loadUserByUsername(email))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("Employee not found with email: " + email);
        verify(employeeRepository).findByEmail(email);
    }
}
