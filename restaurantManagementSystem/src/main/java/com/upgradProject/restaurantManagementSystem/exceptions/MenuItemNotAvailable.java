package com.upgradProject.restaurantManagementSystem.exceptions;

public class MenuItemNotAvailable extends RuntimeException {
    public MenuItemNotAvailable(String message) {
        super(message);
    }
}
