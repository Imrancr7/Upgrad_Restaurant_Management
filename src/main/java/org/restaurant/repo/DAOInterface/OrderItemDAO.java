package org.restaurant.repo.DAOInterface;

import org.restaurant.model.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    OrderItem addOrderItem(OrderItem orderItem);
    OrderItem getOrderItemById(int orderItemId);
    List<OrderItem> getAllOrderItems();
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(int orderItemId);

    List<OrderItem> getOrderItemsByOrderId(int orderId);
}

