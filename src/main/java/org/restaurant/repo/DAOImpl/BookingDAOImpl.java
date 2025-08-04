package org.restaurant.repo.DAOImpl;

import org.restaurant.model.Booking;
import org.restaurant.repo.DAOInterface.BookingDAO;
import org.restaurant.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class BookingDAOImpl implements BookingDAO {
    @Override
    public Booking addBooking(Booking booking) {
        String sql = "INSERT INTO booking (customer_id, table_id, booking_time, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getTableId());
            stmt.setTimestamp(3, Timestamp.valueOf(booking.getBookingTime()));
            stmt.setString(4, "BOOKED");
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Booking.Builder()
                            .bookingId(keys.getInt(1))
                            .customerId(booking.getCustomerId())
                            .tableId(booking.getTableId())
                            .bookingTime(booking.getBookingTime())
                            .status(booking.getStatus())
                            .build();
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM booking WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Booking.Builder()
                        .bookingId(rs.getInt("booking_id"))
                        .customerId(rs.getInt("customer_id"))
                        .tableId(rs.getInt("table_id"))
                        .bookingTime(rs.getTimestamp("booking_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Booking.Builder()
                        .bookingId(rs.getInt("booking_id"))
                        .customerId(rs.getInt("customer_id"))
                        .tableId(rs.getInt("table_id"))
                        .bookingTime(rs.getTimestamp("booking_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void updateBooking(Booking booking) {
        String sql = "UPDATE booking SET customer_id = ?, table_id = ?, booking_time = ?, status = ? WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getTableId());
            stmt.setTimestamp(3, Timestamp.valueOf(booking.getBookingTime()));
            stmt.setString(4, booking.getStatus());
            stmt.setInt(5, booking.getBookingId());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void deleteBooking(int id) {
        String sql = "DELETE FROM booking WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Booking.Builder()
                        .bookingId(rs.getInt("booking_id"))
                        .customerId(rs.getInt("customer_id"))
                        .tableId(rs.getInt("table_id"))
                        .bookingTime(rs.getTimestamp("booking_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

