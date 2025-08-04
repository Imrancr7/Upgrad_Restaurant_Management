package org.restaurant.repo.DAOImpl;


import org.restaurant.config.DBConnection;
import org.restaurant.model.OrderTable;
import org.restaurant.repo.DAOInterface.OrderTableDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderTableDAOImpl implements OrderTableDAO {

    @Override
    public OrderTable addOrder(OrderTable order) {
        String sql = "INSERT INTO order_table (booking_id, waiter_id, order_time, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getBookingId());
            stmt.setInt(2, order.getWaiterId());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getOrderTime()));
            stmt.setString(4, order.getStatus());

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return new OrderTable.Builder()
                        .orderId(keys.getInt(1))
                        .bookingId(order.getBookingId())
                        .waiterId(order.getWaiterId())
                        .orderTime(order.getOrderTime())
                        .status(order.getStatus())
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderTable getOrderById(int id) {
        String sql = "SELECT * FROM order_table WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new OrderTable.Builder()
                        .orderId(rs.getInt("order_id"))
                        .bookingId(rs.getInt("booking_id"))
                        .waiterId(rs.getInt("waiter_id"))
                        .orderTime(rs.getTimestamp("order_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderTable> getAllOrders() {
        List<OrderTable> list = new ArrayList<>();
        String sql = "SELECT * FROM order_table";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new OrderTable.Builder()
                        .orderId(rs.getInt("order_id"))
                        .bookingId(rs.getInt("booking_id"))
                        .waiterId(rs.getInt("waiter_id"))
                        .orderTime(rs.getTimestamp("order_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateOrder(OrderTable order) {
        String sql = "UPDATE order_table SET booking_id = ?, waiter_id = ?, order_time = ?, status = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getBookingId());
            stmt.setInt(2, order.getWaiterId());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getOrderTime()));
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getOrderId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int id) {
        String sql = "DELETE FROM order_table WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderTable> getOrdersByBookingId(int bookingId) {
        String sql = "SELECT * FROM order_table WHERE booking_id = ?";
        List<OrderTable> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new OrderTable.Builder()
                        .orderId(rs.getInt("order_id"))
                        .bookingId(rs.getInt("booking_id"))
                        .waiterId(rs.getInt("waiter_id"))
                        .orderTime(rs.getTimestamp("order_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

