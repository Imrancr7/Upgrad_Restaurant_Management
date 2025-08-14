package com.upgradProject.restaurantManagementSystem.service;

import com.upgradProject.restaurantManagementSystem.dto.EmployeeInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.EmployeeLoginDTO;
import com.upgradProject.restaurantManagementSystem.dto.UserPrincipal;
import com.upgradProject.restaurantManagementSystem.entity.Employee;
import com.upgradProject.restaurantManagementSystem.enums.Role;
import com.upgradProject.restaurantManagementSystem.exceptions.ResourceNotFoundException;
import com.upgradProject.restaurantManagementSystem.repo.EmployeeRepository;
import com.upgradProject.restaurantManagementSystem.service.impls.EmployeeServiceImpl;
import com.upgradProject.restaurantManagementSystem.service.impls.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private JWTService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        reset(employeeRepository, jwtService, authenticationManager);
    }

    @Test
    void createEmployee_SavesAndReturnsEmployee() {
        EmployeeInputDTO dto =  EmployeeInputDTO.builder().build();
        dto.setName("John");
        dto.setRole(Role.MANAGER);
        dto.setEmail("john@example.com");
        dto.setPhone("1234567890");
        dto.setPassword("pass");

        Employee saved = new Employee();
        saved.setName("John");
        saved.setRole(Role.MANAGER);
        saved.setEmail("john@example.com");
        saved.setPhone("1234567890");
        saved.setPassword("pass");

        when(employeeRepository.save(any(Employee.class))).thenReturn(saved);

        Employee result = employeeService.createEmployee(dto);

        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getRole().name()).isEqualTo("MANAGER");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getPhone()).isEqualTo("1234567890");
        assertThat(result.getPassword()).isEqualTo("pass");
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void verifyEmployeeLogin_ReturnsJwtTokenOnSuccess() {
        EmployeeLoginDTO loginDTO = new EmployeeLoginDTO();
        loginDTO.setEmail("john@example.com");
        loginDTO.setPassword("pass");

        Authentication authentication = mock(Authentication.class);
        Employee employee = new Employee();
        employee.setEmail("john@example.com");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(employeeRepository.findByEmail("john@example.com")).thenReturn(Optional.of(employee));
        when(jwtService.generateToken(any(UserPrincipal.class))).thenReturn("jwt-token");

        String token = employeeService.verifyEmployeeLogin(loginDTO);

        assertThat(token).isEqualTo("jwt-token");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(employeeRepository).findByEmail("john@example.com");
        verify(jwtService).generateToken(any(UserPrincipal.class));
    }

    @Test
    void verifyEmployeeLogin_ThrowsAccessDeniedExceptionWhenAuthenticationFails() {
        EmployeeLoginDTO loginDTO = new EmployeeLoginDTO();
        loginDTO.setEmail("john@example.com");
        loginDTO.setPassword("wrong");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        assertThatThrownBy(() -> employeeService.verifyEmployeeLogin(loginDTO))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("Failed to authenticate");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, times(2)).isAuthenticated();
    }

    @Test
    void verifyEmployeeLogin_ThrowsResourceNotFoundExceptionWhenEmployeeIsNull() {
        EmployeeLoginDTO loginDTO = new EmployeeLoginDTO();
        loginDTO.setEmail("notfound@example.com");
        loginDTO.setPassword("pass");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(employeeRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.verifyEmployeeLogin(loginDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Internal Server error");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(employeeRepository).findByEmail("notfound@example.com");
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        Employee e1 = new Employee();
        Employee e2 = new Employee();
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Employee> result = employeeService.getAllEmployees();

        assertThat(result).containsExactly(e1, e2);
        verify(employeeRepository).findAll();
    }

    @Test
    void getAllEmployees_ReturnsEmptyListWhenNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = employeeService.getAllEmployees();

        assertThat(result).isEmpty();
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_ReturnsEmployeeWhenFound() {
        Employee employee = new Employee();
        employee.setId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1);

        assertThat(result).isEqualTo(employee);
        verify(employeeRepository).findById(1);
    }

    @Test
    void getEmployeeById_ThrowsResourceNotFoundExceptionWhenNotFound() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(2))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found with id: 2");
        verify(employeeRepository).findById(2);
    }

    @Test
    void updateEmployee_UpdatesAndReturnsEmployee() {
        Employee existing = new Employee();
        existing.setId(1);
        existing.setName("Old");
        existing.setRole(Role.WAITER);
        existing.setEmail("old@example.com");
        existing.setPhone("111");
        Employee updated = new Employee();
        updated.setName("New");
        updated.setRole(Role.MANAGER);
        updated.setEmail("new@example.com");
        updated.setPhone("222");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(existing)).thenReturn(existing);

        Employee result = employeeService.updateEmployee(1, updated);

        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getRole().name()).isEqualTo("MANAGER");
        assertThat(result.getEmail()).isEqualTo("new@example.com");
        assertThat(result.getPhone()).isEqualTo("222");
        verify(employeeRepository).save(existing);
    }

    @Test
    void updateEmployee_ThrowsResourceNotFoundExceptionWhenNotFound() {
        Employee updated = new Employee();
        when(employeeRepository.findById(5)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployee(5, updated))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found with id: 5");
        verify(employeeRepository).findById(5);
    }

    @Test
    void deleteEmployee_DeletesEmployeeById() {
        employeeService.deleteEmployee(3);
        verify(employeeRepository).deleteById(3);
    }

    @Test
    void setEmployeePassword_UpdatesPassword() {
        Employee employee = new Employee();
        employee.setId(4);
        employee.setPassword("oldpass");
        when(employeeRepository.findById(4)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        employeeService.setEmployeePassword(4, "newpass");

        assertThat(employee.getPassword()).isEqualTo("newpass");
        verify(employeeRepository).save(employee);
    }

    @Test
    void setEmployeePassword_ThrowsResourceNotFoundExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(7)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.setEmployeePassword(7, "pass"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found with id: 7");
        verify(employeeRepository).findById(7);
    }
}
