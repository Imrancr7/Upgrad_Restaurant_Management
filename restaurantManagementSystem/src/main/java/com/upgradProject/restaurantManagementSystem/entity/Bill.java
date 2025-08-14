package com.upgradProject.restaurantManagementSystem.entity;

import com.upgradProject.restaurantManagementSystem.enums.BillStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "generated_time")
    private LocalDateTime generatedTime;

    @Enumerated(EnumType.STRING)
    private BillStatus status;
}
