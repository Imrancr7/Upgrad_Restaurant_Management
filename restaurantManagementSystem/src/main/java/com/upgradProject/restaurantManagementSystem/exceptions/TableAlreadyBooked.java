package com.upgradProject.restaurantManagementSystem.exceptions;

public class TableAlreadyBooked extends RuntimeException {
    public TableAlreadyBooked(String message) {
        super(message);
    }
}
