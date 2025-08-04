package org.restaurant.repo.DAOInterface;

import org.restaurant.model.Payment;

import java.util.List;

public interface PaymentDAO {
    Payment addPayment(Payment payment);
    Payment getPaymentById(int paymentId);
    List<Payment> getAllPayments();
    void updatePayment(Payment payment);
    void deletePayment(int paymentId);

    List<Payment> getPaymentsByBillId(int billId);
}

