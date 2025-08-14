package com.upgradProject.restaurantManagementSystem.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

    private String name;
    private String description;
    private Double price;
    private Boolean available;

}
