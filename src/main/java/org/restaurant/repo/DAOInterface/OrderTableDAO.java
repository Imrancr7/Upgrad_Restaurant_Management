package org.restaurant.repo.DAOInterface;

import org.restaurant.model.OrderTable;

import java.util.List;

public interface OrderTableDAO {
    OrderTable addOrder(OrderTable order);
    OrderTable getOrderById(int orderId);
    List<OrderTable> getAllOrders();
    void updateOrder(OrderTable order);
    void deleteOrder(int orderId);

    List<OrderTable> getOrdersByBookingId(int bookingId);
}
