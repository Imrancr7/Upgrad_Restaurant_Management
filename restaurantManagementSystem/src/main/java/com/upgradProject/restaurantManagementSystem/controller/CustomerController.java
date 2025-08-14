package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.entity.Customer;
import com.upgradProject.restaurantManagementSystem.exceptions.GlobalExceptionHandler;
import com.upgradProject.restaurantManagementSystem.service.interfaces.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        logger.info("Customer created successfully");
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        logger.info("Customer deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
