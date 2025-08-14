package com.upgradProject.restaurantManagementSystem.service.interfaces;



import com.upgradProject.restaurantManagementSystem.dto.BookingInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.BookingResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.Booking;

import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingInputDTO booking);
    List<BookingResponseDTO> getAllBookings();
    Booking getBookingById(int id);
    BookingResponseDTO updateBooking(int id, Booking updatedBooking);
    void deleteBooking(int id);
}
