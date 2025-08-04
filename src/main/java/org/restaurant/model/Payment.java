package org.restaurant.model;

import java.time.LocalDateTime;

public class Payment {
    private final int paymentId;
    private final int billId;
    private final LocalDateTime paymentTime;
    private final double amount;
    private final String paymentMode;

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.billId = builder.billId;
        this.paymentTime = builder.paymentTime;
        this.amount = builder.amount;
        this.paymentMode = builder.paymentMode;
    }

    public static class Builder {
        private int paymentId;
        private int billId;
        private LocalDateTime paymentTime;
        private double amount;
        private String paymentMode;

        public Builder paymentId(int id) { this.paymentId = id; return this; }
        public Builder billId(int id) { this.billId = id; return this; }
        public Builder paymentTime(LocalDateTime time) { this.paymentTime = time; return this; }
        public Builder amount(double amt) { this.amount = amt; return this; }
        public Builder paymentMode(String mode) { this.paymentMode = mode; return this; }
        public Payment build() { return new Payment(this); }
    }

    // Getters...
}

