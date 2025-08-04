package org.restaurant.service.Customer;


import org.restaurant.model.Customer;
import org.restaurant.model.RestaurantTable;
import org.restaurant.repo.DAOImpl.RestaurantTableDAOImpl;
import org.restaurant.repo.DAOInterface.CustomerDAO;
import org.restaurant.repo.DAOImpl.CustomerDAOImpl;
import org.restaurant.repo.DAOInterface.RestaurantTableDAO;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final RestaurantTableDAO restaurantTableDAO;

    public CustomerServiceImpl() {
        this.customerDAO = new CustomerDAOImpl();
        this.restaurantTableDAO = new RestaurantTableDAOImpl(); // Assuming you have a RestaurantTableDAOImpl
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        // Add validation if needed
        return customerDAO.addCustomer(customer);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public void updateCustomerInfo(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    @Override
    public void removeCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }

    @Override
    public List<RestaurantTable> getAvailableTables() {
        return restaurantTableDAO.getAvailableTables();
    }
}

