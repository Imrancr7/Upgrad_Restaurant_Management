package org.restaurant.service.Booking;

import org.restaurant.model.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    void updateBooking(Booking booking);
    void cancelBooking(int id);
    List<Booking> getBookingsByCustomerId(int customerId);
}

