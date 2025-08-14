package com.upgradProject.restaurantManagementSystem.service.impls;

import com.upgradProject.restaurantManagementSystem.dto.EmployeeInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.EmployeeLoginDTO;
import com.upgradProject.restaurantManagementSystem.dto.UserPrincipal;
import com.upgradProject.restaurantManagementSystem.entity.Employee;
import com.upgradProject.restaurantManagementSystem.exceptions.ResourceNotFoundException;
import com.upgradProject.restaurantManagementSystem.repo.EmployeeRepository;
import com.upgradProject.restaurantManagementSystem.service.interfaces.EmployeeService;
import com.upgradProject.restaurantManagementSystem.util.LoggerUtil;
import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerUtil.getLogger(EmployeeServiceImpl.class);


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Employee createEmployee(EmployeeInputDTO employee) {
        Employee newEmployee = new Employee();
        newEmployee.setName(employee.getName());
        newEmployee.setRole(employee.getRole());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setPhone(employee.getPhone());
        newEmployee.setPassword(employee.getPassword());
        return employeeRepository.save(newEmployee);
    }

    @Override
    public String verifyEmployeeLogin(EmployeeLoginDTO employeeLoginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeLoginDTO.getEmail(), employeeLoginDTO.getPassword()));
        Employee employee = employeeRepository.findByEmail(employeeLoginDTO.getEmail()).orElse(null);
        if(authentication.isAuthenticated() && employee!=null ){
            logger.info("User Authenticated Successfully...");
            return jwtService.generateToken(new UserPrincipal(employee));
        }
        else if(!authentication.isAuthenticated()){
            logger.error("Failed to authenticate");
            throw new AccessDeniedException("Failed to authenticate verify whether the details are correct");
        }
        else{
            logger.error("Internal server error");
            throw new ResourceNotFoundException("Internal Server error please login later");
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        logger.info("Fetching employee with id: {}", id);
        if(employeeOptional.isPresent()) {
            return employeeOptional.get();
        } else {
            logger.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
    }

    @Override
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setRole(updatedEmployee.getRole());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        logger.info("Updating employee with id: {}", id);
        return employeeRepository.save(existingEmployee);
    }



    @Override
    public void deleteEmployee(int id) {
        logger.warn("Deleting employee with id: {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    public void setEmployeePassword(int id, String password) {
        Employee employee = getEmployeeById(id);
        employee.setPassword(password);
        logger.info("updating password for employee with id: {}", id);
        employeeRepository.save(employee);
    }

}
