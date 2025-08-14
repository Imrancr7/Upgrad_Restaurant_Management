package com.upgradProject.restaurantManagementSystem.service.impls;

import com.upgradProject.restaurantManagementSystem.controller.OrderController;
import com.upgradProject.restaurantManagementSystem.dto.UserPrincipal;
import com.upgradProject.restaurantManagementSystem.entity.Employee;
import com.upgradProject.restaurantManagementSystem.exceptions.EmployeeNotFoundException;
import com.upgradProject.restaurantManagementSystem.repo.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomEmployeeDetailsService.class);



    public CustomEmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        Employee employee = employeeRepository.findByEmail(username).orElse(null);
        if (employee == null) {
            logger.error("Employee not found with email: {}", username);
            throw new EmployeeNotFoundException("Employee not found with email: " + username);
        }
        return new UserPrincipal(employee);
    }
}
