package com.upgradProject.restaurantManagementSystem.service.impls;

import com.upgradProject.restaurantManagementSystem.controller.OrderController;
import com.upgradProject.restaurantManagementSystem.dto.BookingInputDTO;
import com.upgradProject.restaurantManagementSystem.dto.BookingResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.Booking;
import com.upgradProject.restaurantManagementSystem.entity.Customer;
import com.upgradProject.restaurantManagementSystem.entity.RestaurantTable;
import com.upgradProject.restaurantManagementSystem.enums.BookingStatus;
import com.upgradProject.restaurantManagementSystem.exceptions.ResourceNotFoundException;
import com.upgradProject.restaurantManagementSystem.exceptions.TableAlreadyBooked;
import com.upgradProject.restaurantManagementSystem.repo.BookingRepository;
import com.upgradProject.restaurantManagementSystem.repo.CustomerRepository;
import com.upgradProject.restaurantManagementSystem.repo.RestaurantTableRepository;
import com.upgradProject.restaurantManagementSystem.service.interfaces.BookingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestaurantTableRepository tableRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);


    public BookingServiceImpl(BookingRepository bookingRepository, RestaurantTableRepository tableRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.tableRepository = tableRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingInputDTO booking) {
        RestaurantTable table = tableRepository.findById(booking.getTable_id())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        if (Boolean.FALSE.equals(table.getIsAvailable())) {
            throw new TableAlreadyBooked("Table is already booked");
        }
        Optional<Customer> customer = customerRepository.findById(booking.getCustomer_id());

        if(customer.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found with id: " + booking.getCustomer_id());
        }
        Booking newBooking = new Booking();
        newBooking.setTable(table);
        newBooking.setCustomer(customer.get());
        newBooking.setBookingTime(LocalDateTime.now());
        newBooking.setStatus(BookingStatus.BOOKED);


        table.setIsAvailable(false);
        tableRepository.save(table);
        logger.info("Table with ID: {} is now booked", table.getId());
        newBooking.setStatus(BookingStatus.BOOKED);

        return convertToResponseDTO(bookingRepository.save(newBooking));
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        logger.info("Fetching all bookings");
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public Booking getBookingById(int id) {
        logger.info("Fetching booking with ID: {}", id);
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    @Override
    @Transactional
    public BookingResponseDTO updateBooking(int id, Booking updatedBooking) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        existing.setBookingTime(updatedBooking.getBookingTime());
        existing.setStatus(updatedBooking.getStatus());
        logger.info("Updating booking with ID: {}", id);
        return convertToResponseDTO(bookingRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteBooking(int id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        RestaurantTable table = booking.getTable();
        logger.info("Deleting booking with ID: {}", id);
        table.setIsAvailable(true);
        tableRepository.save(table);
        bookingRepository.deleteById(id);
    }

    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .customerId(booking.getCustomer().getId())
                .customerName(booking.getCustomer().getName())
                .tableId(booking.getTable().getId())
                .bookingTime(booking.getBookingTime())
                .bookingStatus(booking.getStatus().name())
                .build();
    }
}