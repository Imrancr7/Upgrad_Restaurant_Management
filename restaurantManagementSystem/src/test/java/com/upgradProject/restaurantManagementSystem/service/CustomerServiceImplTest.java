package com.upgradProject.restaurantManagementSystem.service;


import com.upgradProject.restaurantManagementSystem.entity.Customer;
import com.upgradProject.restaurantManagementSystem.repo.CustomerRepository;
import com.upgradProject.restaurantManagementSystem.service.impls.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        reset(customerRepository);
    }

    @Test
    void createCustomer_SavesAndReturnsCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertThat(result).isEqualTo(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomer_ThrowsWhenCustomerIsNull() {
        assertThatThrownBy(() -> customerService.createCustomer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer cannot be null");
        verifyNoInteractions(customerRepository);
    }

    @Test
    void deleteCustomer_DeletesWhenCustomerExists() {
        int id = 1;
        when(customerRepository.existsById(id)).thenReturn(true);

        customerService.deleteCustomer(id);

        verify(customerRepository).deleteById(id);
    }

    @Test
    void deleteCustomer_ThrowsWhenCustomerDoesNotExist() {
        int id = 2;
        when(customerRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> customerService.deleteCustomer(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer with id " + id + " does not exist");
        verify(customerRepository, never()).deleteById(anyInt());
    }
}
