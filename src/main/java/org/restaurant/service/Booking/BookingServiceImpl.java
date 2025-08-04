package org.restaurant.service.Booking;

import org.restaurant.model.Booking;
import org.restaurant.repo.DAOInterface.BookingDAO;
import org.restaurant.repo.DAOImpl.BookingDAOImpl;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAOImpl();
    }

    @Override
    public Booking createBooking(Booking booking) {
        // Add additional validation or concurrency logic here if required
        return bookingDAO.addBooking(booking);
    }

    @Override
    public Booking getBookingById(int id) {
        return bookingDAO.getBookingById(id);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    @Override
    public void updateBooking(Booking booking) {
        bookingDAO.updateBooking(booking);
    }

    @Override
    public void cancelBooking(int id) {
        bookingDAO.deleteBooking(id);
    }

    @Override
    public List<Booking> getBookingsByCustomerId(int customerId) {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }
}

