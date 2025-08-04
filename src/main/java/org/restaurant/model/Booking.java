package org.restaurant.model;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class Booking {
    private final int bookingId;
    private final int customerId;
    private final int tableId;
    private final LocalDateTime bookingTime;
    private final String status;

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.customerId = builder.customerId;
        this.tableId = builder.tableId;
        this.bookingTime = builder.bookingTime;
        this.status = builder.status;
    }

    public static class Builder {
        private int bookingId;
        private int customerId;
        private int tableId;
        private LocalDateTime bookingTime;
        private String status;

        public Builder bookingId(int id) { this.bookingId = id; return this; }
        public Builder customerId(int id) { this.customerId = id; return this; }
        public Builder tableId(int id) { this.tableId = id; return this; }
        public Builder bookingTime(LocalDateTime time) { this.bookingTime = time; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Booking build() { return new Booking(this); }
    }

    // Getters...
    public int getBookingId() {
        return bookingId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getTableId() {
        return tableId;
    }
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", tableId=" + tableId +
                ", bookingTime=" + bookingTime +
                ", status='" + status + '\'' +
                '}';
    }
}
