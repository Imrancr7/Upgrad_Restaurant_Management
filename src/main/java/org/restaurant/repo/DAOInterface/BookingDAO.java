package org.restaurant.repo.DAOInterface;

import org.restaurant.model.Booking;

import java.util.List;

public interface BookingDAO {
    Booking addBooking(Booking booking);
    Booking getBookingById(int bookingId);
    List<Booking> getAllBookings();
    void updateBooking(Booking booking);
    void deleteBooking(int bookingId);

    List<Booking> getBookingsByCustomerId(int customerId);
}
