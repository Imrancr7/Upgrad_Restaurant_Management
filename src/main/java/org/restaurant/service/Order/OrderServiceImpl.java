package org.restaurant.service.Order;


import org.restaurant.model.OrderItem;
import org.restaurant.model.OrderTable;
import org.restaurant.repo.DAOInterface.OrderItemDAO;
import org.restaurant.repo.DAOInterface.OrderTableDAO;
import org.restaurant.service.Bill.BillService;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderTableDAO orderTableDAO;
    private final OrderItemDAO orderItemDAO;
    private final BillService billService;

    public OrderServiceImpl(OrderTableDAO orderTableDAO, OrderItemDAO orderItemDAO, BillService billService) {
        this.orderTableDAO = orderTableDAO;
        this.orderItemDAO = orderItemDAO;
        this.billService = billService;
    }

    @Override
    public void placeOrder(int bookingId, int waiterId, List<OrderItem> items) {
        // Step 1: Create Order
        OrderTable order = new OrderTable.Builder()
                .bookingId(bookingId)
                .waiterId(waiterId)
                .orderTime(LocalDateTime.now())
                .status("PLACED")
                .build();

        OrderTable savedOrder = orderTableDAO.addOrder(order);

        // Step 2: Insert Order Items
        for (OrderItem item : items) {
            item.setOrderId(savedOrder.getOrderId());
            orderItemDAO.addOrderItem(item);
        }

        // Step 3: Simulate Kitchen
        simulateKitchen(savedOrder.getOrderId());

        // Step 4: Generate Bill
        billService.generateBill(savedOrder.getOrderId());
    }

    private void simulateKitchen(int orderId) {
        System.out.println("Preparing Order ID: " + orderId);
        try {
            Thread.sleep(2000); // simulate 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        OrderTable order = orderTableDAO.getOrderById(orderId);
        order.setStatus("COMPLETED");
        orderTableDAO.updateOrder(order);
        System.out.println("Order ID: " + orderId + " is COMPLETED.");
    }
}
