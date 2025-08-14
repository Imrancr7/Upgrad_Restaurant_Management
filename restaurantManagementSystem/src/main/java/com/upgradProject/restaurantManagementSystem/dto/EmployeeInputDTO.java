package com.upgradProject.restaurantManagementSystem.dto;

import com.upgradProject.restaurantManagementSystem.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeInputDTO {
    private String name;
    private String email;
    private String phone;

    private Role role;

    private String password;
}
