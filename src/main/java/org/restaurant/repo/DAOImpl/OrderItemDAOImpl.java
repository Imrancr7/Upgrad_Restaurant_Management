package org.restaurant.repo.DAOImpl;

import org.restaurant.config.DBConnection;
import org.restaurant.model.OrderItem;
import org.restaurant.repo.DAOInterface.OrderItemDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO  {

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
       String sql = "INSERT INTO order_item (order_id, item_id, quantity) VALUES (?, ?, ?)";
       try(Connection conn = DBConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           stmt.setInt(1, orderItem.getOrderId());
           stmt.setInt(2, orderItem.getItemId());
           stmt.setInt(3, orderItem.getQuantity());

           stmt.executeUpdate();
           try (ResultSet keys = stmt.getGeneratedKeys()) {
               if (keys.next()) {
                   return new OrderItem.Builder()
                           .orderItemId(keys.getInt(1))
                           .orderId(orderItem.getOrderId())
                           .itemId(orderItem.getItemId())
                           .quantity(orderItem.getQuantity())
                           .build();
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        String sql = "SELECT * FROM order_item WHERE order_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new OrderItem.Builder()
                        .orderItemId(rs.getInt("order_item_id"))
                        .orderId(rs.getInt("order_id"))
                        .itemId(rs.getInt("menu_item_id"))
                        .quantity(rs.getInt("quantity"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        String sql = "SELECT * FROM order_item";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            List<OrderItem> orderItems = new ArrayList<>();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem.Builder()
                        .orderItemId(rs.getInt("order_item_id"))
                        .orderId(rs.getInt("order_id"))
                        .itemId(rs.getInt("menu_item_id"))
                        .quantity(rs.getInt("quantity"))
                        .build();
                orderItems.add(orderItem);
            }
            return orderItems;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {

        String sql = "UPDATE order_item SET order_id = ?, item_id = ?, quantity = ? WHERE order_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getItemId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setInt(4, orderItem.getOrderItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void deleteOrderItem(int orderItemId) {

        String sql = "DELETE FROM order_item WHERE order_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {

        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem.Builder()
                        .orderItemId(rs.getInt("order_item_id"))
                        .orderId(rs.getInt("order_id"))
                        .itemId(rs.getInt("item_id"))
                        .quantity(rs.getInt("quantity"))
                        .build();
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}
