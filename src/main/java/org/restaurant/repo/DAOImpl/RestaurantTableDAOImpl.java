package org.restaurant.repo.DAOImpl;

import org.restaurant.config.DBConnection;
import org.restaurant.model.RestaurantTable;
import org.restaurant.repo.DAOInterface.RestaurantTableDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTableDAOImpl implements RestaurantTableDAO {
    @Override
    public RestaurantTable addTable(RestaurantTable table) {
        String sql = "INSERT INTO restaurant_table (table_number, capacity, is_available) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, table.getTableNumber());
            stmt.setInt(2, table.getCapacity());
            stmt.setBoolean(3, table.isAvailable());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new RestaurantTable.Builder()
                            .tableId(keys.getInt(1))
                            .tableNumber(table.getTableNumber())
                            .capacity(table.getCapacity())
                            .isAvailable(table.isAvailable())
                            .build();
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public RestaurantTable getTableById(int id) {
        String sql = "SELECT * FROM restaurant_table WHERE table_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RestaurantTable.Builder()
                        .tableId(rs.getInt("table_id"))
                        .tableNumber(rs.getString("table_number"))
                        .capacity(rs.getInt("capacity"))
                        .isAvailable(rs.getBoolean("is_available"))
                        .build();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<RestaurantTable> getAllTables() {
        List<RestaurantTable> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_table";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RestaurantTable table = new RestaurantTable.Builder()
                        .tableId(rs.getInt("table_id"))
                        .tableNumber(rs.getString("table_number"))
                        .capacity(rs.getInt("capacity"))
                        .isAvailable(rs.getBoolean("is_available"))
                        .build();
                list.add(table);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void updateTable(RestaurantTable table) {
        String sql = "UPDATE restaurant_table SET table_number = ?, capacity = ?, is_available = ? WHERE table_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, table.getTableNumber());
            stmt.setInt(2, table.getCapacity());
            stmt.setBoolean(3, table.isAvailable());
            stmt.setInt(4, table.getTableId());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void deleteTable(int id) {
        String sql = "DELETE FROM restaurant_table WHERE table_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<RestaurantTable> getAvailableTables() {
        List<RestaurantTable> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_table WHERE is_available = TRUE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new RestaurantTable.Builder()
                        .tableId(rs.getInt("table_id"))
                        .tableNumber(rs.getString("table_number"))
                        .capacity(rs.getInt("capacity"))
                        .isAvailable(rs.getBoolean("is_available"))
                        .build());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

