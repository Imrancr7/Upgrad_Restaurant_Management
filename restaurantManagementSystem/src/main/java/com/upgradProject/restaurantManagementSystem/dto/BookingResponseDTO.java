package com.upgradProject.restaurantManagementSystem.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDTO {
    private int bookingId;
    private int customerId;
    private String customerName;
    private int tableId;
    private String bookingStatus;
    private LocalDateTime bookingTime;
}
