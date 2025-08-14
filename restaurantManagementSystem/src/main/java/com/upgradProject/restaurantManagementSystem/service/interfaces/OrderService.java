package com.upgradProject.restaurantManagementSystem.service.interfaces;

import com.upgradProject.restaurantManagementSystem.dto.OrderRequestDTO;
import com.upgradProject.restaurantManagementSystem.dto.OrderResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO updateOrderStatus(int orderId, String status);
    void deleteOrder(int orderId);
}
