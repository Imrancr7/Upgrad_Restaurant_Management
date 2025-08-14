package com.upgradProject.restaurantManagementSystem.service.interfaces;

import com.upgradProject.restaurantManagementSystem.entity.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    void deleteCustomer(int id);
}
