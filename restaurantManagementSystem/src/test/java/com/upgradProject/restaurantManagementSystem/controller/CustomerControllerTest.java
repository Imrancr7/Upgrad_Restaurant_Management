package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.entity.Customer;
import com.upgradProject.restaurantManagementSystem.service.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        reset(customerService);
    }

    @Test
    void createCustomer_ReturnsCreatedCustomer() {
        Customer input = new Customer();
        Customer created = new Customer();
        when(customerService.createCustomer(input)).thenReturn(created);

        ResponseEntity<Customer> result = customerController.createCustomer(input);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(created);
        verify(customerService).createCustomer(input);
    }

    @Test
    void createCustomer_ReturnsNullWhenServiceReturnsNull() {
        Customer input = new Customer();
        when(customerService.createCustomer(input)).thenReturn(null);

        ResponseEntity<Customer> result = customerController.createCustomer(input);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNull();
        verify(customerService).createCustomer(input);
    }

    @Test
    void deleteCustomer_ReturnsNoContentStatus() {
        int id = 42;

        ResponseEntity<Void> result = customerController.deleteCustomer(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        assertThat(result.getBody()).isNull();
        verify(customerService).deleteCustomer(id);
    }

    @Test
    void deleteCustomer_DoesNotThrowWhenCustomerDoesNotExist() {
        int id = 404;
        doNothing().when(customerService).deleteCustomer(id);

        ResponseEntity<Void> result = customerController.deleteCustomer(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        assertThat(result.getBody()).isNull();
        verify(customerService).deleteCustomer(id);
    }
}
