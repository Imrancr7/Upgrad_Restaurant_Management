package com.upgradProject.restaurantManagementSystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private int bookingId;
    private int waiterId;
    private List<OrderItemDTO> items;
}
