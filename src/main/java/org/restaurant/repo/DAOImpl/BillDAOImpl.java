package org.restaurant.repo.DAOImpl;

import org.restaurant.config.DBConnection;
import org.restaurant.model.Bill;
import org.restaurant.repo.DAOInterface.BillDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BillDAOImpl implements BillDAO {

    @Override
    public Bill addBill(Bill bill) {
        String sql = "INSERT INTO bill (order_id, total_amount, generated_time, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bill.getOrderId());
            stmt.setDouble(2, bill.getTotalAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(bill.getGeneratedTime()));
            stmt.setString(4, bill.getStatus());

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return new Bill.Builder()
                        .billId(keys.getInt(1))
                        .orderId(bill.getOrderId())
                        .totalAmount(bill.getTotalAmount())
                        .generatedTime(bill.getGeneratedTime())
                        .status(bill.getStatus())
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Bill getBillById(int id) {
        String sql = "SELECT * FROM bill WHERE bill_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bill.Builder()
                        .billId(rs.getInt("bill_id"))
                        .orderId(rs.getInt("order_id"))
                        .totalAmount(rs.getDouble("total_amount"))
                        .generatedTime(rs.getTimestamp("generated_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM bill";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill.Builder()
                        .billId(rs.getInt("bill_id"))
                        .orderId(rs.getInt("order_id"))
                        .totalAmount(rs.getDouble("total_amount"))
                        .generatedTime(rs.getTimestamp("generated_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build();
                list.add(bill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateBill(Bill bill) {
        String sql = "UPDATE bill SET order_id = ?, total_amount = ?, generated_time = ?, status = ? WHERE bill_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getOrderId());
            stmt.setDouble(2, bill.getTotalAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(bill.getGeneratedTime()));
            stmt.setString(4, bill.getStatus());
            stmt.setInt(5, bill.getBillId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBill(int id) {
        String sql = "DELETE FROM bill WHERE bill_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bill getBillByOrderId(int orderId) {
        String sql = "SELECT * FROM bill WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(2, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bill.Builder()
                        .billId(rs.getInt("bill_id"))
                        .orderId(rs.getInt("order_id"))
                        .totalAmount(rs.getDouble("total_amount"))
                        .generatedTime(rs.getTimestamp("generated_time").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build();
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
