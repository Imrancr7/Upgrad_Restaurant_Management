package org.restaurant.repo.DAOInterface;

import org.restaurant.model.Bill;

import java.util.List;

public interface BillDAO {
    Bill addBill(Bill bill);
    Bill getBillById(int billId);
    List<Bill> getAllBills();
    void updateBill(Bill bill);
    void deleteBill(int billId);

    Bill getBillByOrderId(int orderId);
}

