package org.restaurant.repo.DAOInterface;

import org.restaurant.model.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
}

