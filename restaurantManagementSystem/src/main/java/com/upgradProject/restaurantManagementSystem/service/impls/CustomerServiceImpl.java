package com.upgradProject.restaurantManagementSystem.service.impls;

import com.upgradProject.restaurantManagementSystem.controller.OrderController;
import com.upgradProject.restaurantManagementSystem.entity.Customer;
import com.upgradProject.restaurantManagementSystem.repo.CustomerRepository;
import com.upgradProject.restaurantManagementSystem.service.interfaces.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private  final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);


    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        if(customer == null){
            logger.error("Attempted to create a null customer");
            throw new IllegalArgumentException("Customer cannot be null");
        }
        logger.info("Creating customer with email: {}", customer.getEmail());
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        if(!customerRepository.existsById(id)){
            logger.error("Attempted to delete non-existing customer with id: {}", id);
            throw new IllegalArgumentException("Customer with id " + id + " does not exist");
        }
        logger.warn("Deleting customer with id: {}", id);
        customerRepository.deleteById(id);
    }
}
