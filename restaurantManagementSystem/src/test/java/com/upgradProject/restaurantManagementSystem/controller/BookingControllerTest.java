package com.upgradProject.restaurantManagementSystem.controller;// File: src/test/java/com/upgradProject/restaurantManagementSystem/controller/BookingControllerTest.java

import com.upgradProject.restaurantManagementSystem.dto.BookingInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.BookingResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.Booking;
import com.upgradProject.restaurantManagementSystem.service.interfaces.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        reset(bookingService);
    }

    @Test
    void createBooking_ReturnsCreatedBookingResponse() {
        BookingInputDTO input = new BookingInputDTO();
        BookingResponseDTO response = BookingResponseDTO.builder().build();
        when(bookingService.createBooking(input)).thenReturn(response);

        ResponseEntity<BookingResponseDTO> result = bookingController.createBooking(input);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(response);
        verify(bookingService).createBooking(input);
    }

    @Test
    void getAllBookings_ReturnsListOfBookings() {
        List<BookingResponseDTO> bookings = Arrays.asList(BookingResponseDTO.builder().build(), BookingResponseDTO.builder().build());
        when(bookingService.getAllBookings()).thenReturn(bookings);

        ResponseEntity<List<BookingResponseDTO>> result = bookingController.getAllBookings();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).containsExactlyElementsOf(bookings);
        verify(bookingService).getAllBookings();
    }

    @Test
    void getAllBookings_ReturnsEmptyListWhenNoBookings() {
        when(bookingService.getAllBookings()).thenReturn(Collections.emptyList());

        ResponseEntity<List<BookingResponseDTO>> result = bookingController.getAllBookings();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEmpty();
        verify(bookingService).getAllBookings();
    }

    @Test
    void getBookingById_ReturnsBookingWhenFound() {
        int id = 1;
        Booking booking = new Booking();
        when(bookingService.getBookingById(id)).thenReturn(booking);

        ResponseEntity<Booking> result = bookingController.getBookingById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(booking);
        verify(bookingService).getBookingById(id);
    }

    @Test
    void updateBooking_ReturnsUpdatedBookingResponse() {
        int id = 2;
        Booking updatedBooking = new Booking();
        BookingResponseDTO response = BookingResponseDTO.builder().build();
        when(bookingService.updateBooking(id, updatedBooking)).thenReturn(response);

        ResponseEntity<BookingResponseDTO> result = bookingController.updateBooking(id, updatedBooking);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(response);
        verify(bookingService).updateBooking(id, updatedBooking);
    }

    @Test
    void deleteBooking_ReturnsSuccessMessage() {
        int id = 3;

        ResponseEntity<String> result = bookingController.deleteBooking(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Booking deleted successfully");
        verify(bookingService).deleteBooking(id);
    }
}