package com.upgradProject.restaurantManagementSystem.service;

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
import com.upgradProject.restaurantManagementSystem.service.impls.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RestaurantTableRepository tableRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        reset(bookingRepository, tableRepository, customerRepository);
    }

    @Test
    void createBooking_SuccessfulBookingReturnsResponseDTO() {
        BookingInputDTO input = new BookingInputDTO();
        input.setTable_id(1);
        input.setCustomer_id(2);

        RestaurantTable table = new RestaurantTable();
        table.setId(1);
        table.setIsAvailable(true);

        Customer customer = new Customer();
        customer.setId(2);
        customer.setName("John Doe");

        Booking savedBooking = new Booking();
        savedBooking.setId(10);
        savedBooking.setTable(table);
        savedBooking.setCustomer(customer);
        savedBooking.setBookingTime(LocalDateTime.now());
        savedBooking.setStatus(BookingStatus.BOOKED);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(customerRepository.findById(2)).thenReturn(Optional.of(customer));
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(tableRepository.save(any(RestaurantTable.class))).thenReturn(table);

        BookingResponseDTO result = bookingService.createBooking(input);

        assertThat(result.getBookingId()).isEqualTo(10);
        assertThat(result.getCustomerId()).isEqualTo(2);
        assertThat(result.getCustomerName()).isEqualTo("John Doe");
        assertThat(result.getTableId()).isEqualTo(1);
        assertThat(result.getBookingStatus()).isEqualTo("BOOKED");
        verify(tableRepository).save(table);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void createBooking_ThrowsWhenTableNotFound() {
        BookingInputDTO input = new BookingInputDTO();
        input.setTable_id(1);

        when(tableRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.createBooking(input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Table not found");
    }

    @Test
    void createBooking_ThrowsWhenTableAlreadyBooked() {
        BookingInputDTO input = new BookingInputDTO();
        input.setTable_id(1);

        RestaurantTable table = new RestaurantTable();
        table.setId(1);
        table.setIsAvailable(false);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));

        assertThatThrownBy(() -> bookingService.createBooking(input))
                .isInstanceOf(TableAlreadyBooked.class)
                .hasMessageContaining("Table is already booked");
    }

    @Test
    void createBooking_ThrowsWhenCustomerNotFound() {
        BookingInputDTO input = new BookingInputDTO();
        input.setTable_id(1);
        input.setCustomer_id(2);

        RestaurantTable table = new RestaurantTable();
        table.setId(1);
        table.setIsAvailable(true);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(customerRepository.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.createBooking(input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer not found with id: 2");
    }

    @Test
    void getAllBookings_ReturnsListOfBookingResponseDTOs() {
        Booking booking1 = new Booking();
        booking1.setId(1);
        Customer customer1 = new Customer();
        customer1.setId(2);
        customer1.setName("Alice");
        RestaurantTable table1 = new RestaurantTable();
        table1.setId(3);
        booking1.setCustomer(customer1);
        booking1.setTable(table1);
        booking1.setBookingTime(LocalDateTime.now());
        booking1.setStatus(BookingStatus.BOOKED);

        Booking booking2 = new Booking();
        booking2.setId(4);
        Customer customer2 = new Customer();
        customer2.setId(5);
        customer2.setName("Bob");
        RestaurantTable table2 = new RestaurantTable();
        table2.setId(6);
        booking2.setCustomer(customer2);
        booking2.setTable(table2);
        booking2.setBookingTime(LocalDateTime.now());
        booking2.setStatus(BookingStatus.BOOKED);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<BookingResponseDTO> result = bookingService.getAllBookings();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCustomerName()).isEqualTo("Alice");
        assertThat(result.get(1).getCustomerName()).isEqualTo("Bob");
    }

    @Test
    void getAllBookings_ReturnsEmptyListWhenNoBookings() {
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

        List<BookingResponseDTO> result = bookingService.getAllBookings();

        assertThat(result).isEmpty();
    }

    @Test
    void getBookingById_ReturnsBookingWhenFound() {
        Booking booking = new Booking();
        booking.setId(1);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(1);

        assertThat(result).isEqualTo(booking);
    }

    @Test
    void getBookingById_ThrowsWhenNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.getBookingById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found");
    }

    @Test
    void updateBooking_UpdatesAndReturnsBookingResponseDTO() {
        Booking existing = new Booking();
        existing.setId(1);
        existing.setBookingTime(LocalDateTime.now().minusDays(1));
        existing.setStatus(BookingStatus.BOOKED);

        Booking updated = new Booking();
        updated.setBookingTime(LocalDateTime.now());
        updated.setStatus(BookingStatus.CANCELLED);

        Booking saved = new Booking();
        saved.setId(1);
        saved.setBookingTime(updated.getBookingTime());
        saved.setStatus(updated.getStatus());
        Customer customer = new Customer();
        customer.setId(2);
        customer.setName("Test");
        saved.setCustomer(customer);
        RestaurantTable table = new RestaurantTable();
        table.setId(3);
        saved.setTable(table);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(existing));
        when(bookingRepository.save(existing)).thenReturn(saved);

        BookingResponseDTO result = bookingService.updateBooking(1, updated);

        assertThat(result.getBookingId()).isEqualTo(1);
        assertThat(result.getBookingStatus()).isEqualTo("CANCELLED");
    }

    @Test
    void updateBooking_ThrowsWhenBookingNotFound() {
        Booking updated = new Booking();
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.updateBooking(1, updated))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found");
    }

    @Test
    void deleteBooking_DeletesBookingAndSetsTableAvailable() {
        Booking booking = new Booking();
        booking.setId(1);
        RestaurantTable table = new RestaurantTable();
        table.setId(2);
        table.setIsAvailable(false);
        booking.setTable(table);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        bookingService.deleteBooking(1);

        assertThat(table.getIsAvailable()).isTrue();
        verify(tableRepository).save(table);
        verify(bookingRepository).deleteById(1);
    }

    @Test
    void deleteBooking_ThrowsWhenBookingNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.deleteBooking(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found");
    }
}
