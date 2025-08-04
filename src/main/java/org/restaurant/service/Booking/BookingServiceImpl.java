package org.restaurant.service.Booking;

import org.restaurant.model.Booking;
import org.restaurant.model.RestaurantTable;
import org.restaurant.repo.DAOImpl.RestaurantTableDAOImpl;
import org.restaurant.repo.DAOInterface.BookingDAO;
import org.restaurant.repo.DAOImpl.BookingDAOImpl;
import org.restaurant.repo.DAOInterface.RestaurantTableDAO;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final RestaurantTableDAO restaurantTableDAO;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAOImpl();
        this.restaurantTableDAO = new RestaurantTableDAOImpl(); // Assuming you have a RestaurantTableDAOImpl
    }

    @Override
    public Booking createBooking(Booking booking) {
        // Add additional validation or concurrency logic here if required
        restaurantTableDAO.updateTableAvailability(booking.getTableId(), false); // Mark the table as unavailable when booking is created
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
        Booking booking = bookingDAO.getBookingById(id);
        restaurantTableDAO.updateTableAvailability(booking.getTableId(), true); // Mark the table as available when booking is cancelled
        bookingDAO.deleteBooking(id);
    }

    @Override
    public List<Booking> getBookingsByCustomerId(int customerId) {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }
}

