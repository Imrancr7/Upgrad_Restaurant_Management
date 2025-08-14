package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.dto.BookingInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.BookingResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.Booking;
import com.upgradProject.restaurantManagementSystem.exceptions.GlobalExceptionHandler;
import com.upgradProject.restaurantManagementSystem.service.interfaces.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingInputDTO booking) {
        BookingResponseDTO createdBooking = bookingService.createBooking(booking);
        logger.info("Booking created successfully with ID: {}", createdBooking.getBookingId());
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }


    // Get all bookings
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(
            @PathVariable int id,
            @RequestBody Booking updatedBooking
    ) {
        logger.info("Updating booking with ID: {}", id);
        return ResponseEntity.ok(bookingService.updateBooking(id, updatedBooking));
    }

    // Delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        logger.info("Booking with ID: {} deleted successfully", id);
        return ResponseEntity.ok("Booking deleted successfully");
    }


}
