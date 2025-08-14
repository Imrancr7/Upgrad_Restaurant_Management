package com.upgradProject.restaurantManagementSystem.exceptions;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFound_ReturnsNotFoundStatusAndMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource missing");
        ResponseEntity<String> response = handler.handleResourceNotFound(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Resource missing");
    }

    @Test
    void handleMenuItemNotAvailable_ReturnsNotFoundStatusAndMessage() {
        MenuItemNotAvailable ex = new MenuItemNotAvailable("Menu item not available");
        ResponseEntity<String> response = handler.handleMenuItemNotAvailable(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Menu item not available");
    }

    @Test
    void handleEmployeeNotFound_ReturnsNotFoundStatusAndMessage() {
        EmployeeNotFoundException ex = new EmployeeNotFoundException("Employee not found");
        ResponseEntity<String> response = handler.handleEmployeeNotFound(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Employee not found");
    }

    @Test
    void handleTableAlreadyBooked_ReturnsConflictStatusAndMessage() {
        TableAlreadyBooked ex = new TableAlreadyBooked("Table already booked");
        ResponseEntity<String> response = handler.handleTableAlreadyBooked(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isEqualTo("Table already booked");
    }

    @Test
    void handleBadRequest_ReturnsBadRequestStatusAndMessage() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad request");
        ResponseEntity<String> response = handler.handleBadRequest(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Bad request");
    }

    @Test
    void handleGeneralException_ReturnsInternalServerErrorAndMessage() {
        Exception ex = new Exception("Unexpected error");
        ResponseEntity<String> response = handler.handleGeneralException(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("An unexpected error occurred: Unexpected error");
    }
}
