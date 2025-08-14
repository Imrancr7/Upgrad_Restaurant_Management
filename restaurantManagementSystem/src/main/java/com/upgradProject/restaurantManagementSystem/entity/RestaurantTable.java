package com.upgradProject.restaurantManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant_table")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private int id;

    @Column(name = "table_number")
    private String tableNumber;

    private Integer capacity;

    @Column(name = "is_available")
    private Boolean isAvailable;

}
