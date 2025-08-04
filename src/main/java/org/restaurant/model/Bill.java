package org.restaurant.model;

import java.time.LocalDateTime;

public class Bill {
    private final int billId;
    private final int orderId;
    private final double totalAmount;
    private final LocalDateTime generatedTime;
    private final String status;

    private Bill(Builder builder) {
        this.billId = builder.billId;
        this.orderId = builder.orderId;
        this.totalAmount = builder.totalAmount;
        this.generatedTime = builder.generatedTime;
        this.status = builder.status;
    }

    public static class Builder {
        private int billId;
        private int orderId;
        private double totalAmount;
        private LocalDateTime generatedTime;
        private String status;

        public Builder billId(int id) { this.billId = id; return this; }
        public Builder orderId(int id) { this.orderId = id; return this; }
        public Builder totalAmount(double amt) { this.totalAmount = amt; return this; }
        public Builder generatedTime(LocalDateTime time) { this.generatedTime = time; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Bill build() { return new Bill(this); }
    }

    // Getters...
    public int getBillId() {
        return billId;
    }
    public int getOrderId() {
        return orderId;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }
    public String getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", orderId=" + orderId +
                ", totalAmount=" + totalAmount +
                ", generatedTime=" + generatedTime +
                ", status='" + status + '\'' +
                '}';
    }
}
