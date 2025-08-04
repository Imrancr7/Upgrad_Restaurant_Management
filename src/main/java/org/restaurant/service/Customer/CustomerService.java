package org.restaurant.service.Customer;

import org.restaurant.model.Customer;
import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    Customer findCustomerById(int id);
    List<Customer> getAllCustomers();
    void updateCustomerInfo(Customer customer);
    void removeCustomer(int id);
}

