package org.restaurant;

import org.restaurant.config.DBConnection;
import org.restaurant.repo.DAOImpl.BillDAOImpl;
import org.restaurant.repo.DAOImpl.MenuItemDAOImpl;
import org.restaurant.repo.DAOImpl.OrderItemDAOImpl;
import org.restaurant.service.Bill.BillService;
import org.restaurant.service.Bill.BillServiceImpl;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        BillDAOImpl billDAO = new BillDAOImpl();
        OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
        MenuItemDAOImpl menuItemDAO = new MenuItemDAOImpl();

        // Inject into the service
        BillService billService = new BillServiceImpl(billDAO, orderItemDAO, menuItemDAO);

        // Test case: generate bill for an existing order ID
        int orderIdToGenerateBillFor = 1; // Make sure order ID 1 exists in DB
        billService.generateBill(orderIdToGenerateBillFor);

    }
}
