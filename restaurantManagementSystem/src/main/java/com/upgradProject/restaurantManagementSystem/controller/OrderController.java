package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.dto.OrderRequestDTO;
import com.upgradProject.restaurantManagementSystem.dto.OrderResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.Order;
import com.upgradProject.restaurantManagementSystem.exceptions.GlobalExceptionHandler;
import com.upgradProject.restaurantManagementSystem.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO dto) {
        OrderResponseDTO placedOrder = orderService.placeOrder(dto);
        logger.info("Order placed successfully");
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable int orderId, @RequestParam String status) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        logger.info("Order deleted successfully");
        return ResponseEntity.ok("Order deleted successfully");
    }

}
