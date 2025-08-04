package org.restaurant.repo.DAOImpl;


import org.restaurant.model.MenuItem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.restaurant.config.DBConnection;
import org.restaurant.repo.DAOInterface.MenuItemDAO;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAOImpl implements MenuItemDAO {

    @Override
    public MenuItem addMenuItem(MenuItem item) {
        String sql = "INSERT INTO menu_item (name, description, price, available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setBoolean(4, item.isAvailable());

            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new MenuItem.Builder()
                            .itemId(keys.getInt(1))
                            .name(item.getName())
                            .description(item.getDescription())
                            .price(item.getPrice())
                            .available(item.isAvailable())
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MenuItem getMenuItemById(int id) {
        String sql = "SELECT * FROM menu_item WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MenuItem.Builder()
                        .itemId(rs.getInt("item_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .available(rs.getBoolean("available"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_item";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new MenuItem.Builder()
                        .itemId(rs.getInt("item_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .available(rs.getBoolean("available"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateMenuItem(MenuItem item) {
        String sql = "UPDATE menu_item SET name = ?, description = ?, price = ?, available = ? WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setBoolean(4, item.isAvailable());
            stmt.setInt(5, item.getItemId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMenuItem(int id) {
        String sql = "DELETE FROM menu_item WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        String sql = "SELECT * FROM menu_item WHERE available = true";
        List<MenuItem> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(new MenuItem.Builder()
                            .itemId(rs.getInt("item_id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .price(rs.getDouble("price"))
                            .available(rs.getBoolean("available"))
                            .build());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }
}

