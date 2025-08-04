package org.restaurant.service.Order;

import org.restaurant.model.OrderItem;

import java.util.List;

public interface OrderService {
    int placeOrder(int bookingId, int waiterId, List<OrderItem> items);
}

