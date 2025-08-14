package com.upgradProject.restaurantManagementSystem.entity;


import com.upgradProject.restaurantManagementSystem.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int id;

    private String name;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;
}
