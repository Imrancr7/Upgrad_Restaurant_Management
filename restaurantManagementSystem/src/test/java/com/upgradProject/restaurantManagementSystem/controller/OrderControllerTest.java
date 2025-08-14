package com.upgradProject.restaurantManagementSystem.controller;


import com.upgradProject.restaurantManagementSystem.dto.OrderRequestDTO;
import com.upgradProject.restaurantManagementSystem.dto.OrderResponseDTO;
import com.upgradProject.restaurantManagementSystem.service.interfaces.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        reset(orderService);
    }

    @Test
    void placeOrder_ReturnsCreatedOrderResponse() {
        OrderRequestDTO request = new OrderRequestDTO();
        OrderResponseDTO response = OrderResponseDTO.builder().build();
        when(orderService.placeOrder(request)).thenReturn(response);

        ResponseEntity<OrderResponseDTO> result = orderController.placeOrder(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(response);
        verify(orderService).placeOrder(request);
    }

    @Test
    void placeOrder_ReturnsNullWhenServiceReturnsNull() {
        OrderRequestDTO request = new OrderRequestDTO();
        when(orderService.placeOrder(request)).thenReturn(null);

        ResponseEntity<OrderResponseDTO> result = orderController.placeOrder(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNull();
        verify(orderService).placeOrder(request);
    }

    @Test
    void getAllOrders_ReturnsListOfOrderResponses() {
        List<OrderResponseDTO> orders = Arrays.asList(OrderResponseDTO.builder().build(), OrderResponseDTO.builder().build());
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<OrderResponseDTO>> result = orderController.getAllOrders();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).containsExactlyElementsOf(orders);
        verify(orderService).getAllOrders();
    }

    @Test
    void getAllOrders_ReturnsEmptyListWhenNoOrders() {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrderResponseDTO>> result = orderController.getAllOrders();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEmpty();
        verify(orderService).getAllOrders();
    }

    @Test
    void updateOrderStatus_ReturnsUpdatedOrderResponse() {
        int orderId = 1;
        String status = "SERVED";
        OrderResponseDTO updated = OrderResponseDTO.builder().build();
        when(orderService.updateOrderStatus(orderId, status)).thenReturn(updated);

        ResponseEntity<OrderResponseDTO> result = orderController.updateOrderStatus(orderId, status);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(updated);
        verify(orderService).updateOrderStatus(orderId, status);
    }

    @Test
    void updateOrderStatus_ReturnsNullWhenOrderNotFound() {
        int orderId = 99;
        String status = "CANCELLED";
        when(orderService.updateOrderStatus(orderId, status)).thenReturn(null);

        ResponseEntity<OrderResponseDTO> result = orderController.updateOrderStatus(orderId, status);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNull();
        verify(orderService).updateOrderStatus(orderId, status);
    }

    @Test
    void deleteOrder_ReturnsSuccessMessage() {
        int orderId = 5;
        doNothing().when(orderService).deleteOrder(orderId);

        ResponseEntity<String> result = orderController.deleteOrder(orderId);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Order deleted successfully");
        verify(orderService).deleteOrder(orderId);
    }
}
