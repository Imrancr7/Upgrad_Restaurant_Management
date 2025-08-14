package com.upgradProject.restaurantManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class OrderResponseDTO {
    private int orderId;
    private int bookingId;
    private int waiterId;
    private LocalDateTime orderTime;
    private String status;
}
