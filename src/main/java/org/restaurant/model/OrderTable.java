package org.restaurant.model;

import java.time.LocalDateTime;

public class OrderTable {
    private final int orderId;
    private final int bookingId;
    private final int waiterId;
    private final LocalDateTime orderTime;
    private String status;

    private OrderTable(Builder builder) {
        this.orderId = builder.orderId;
        this.bookingId = builder.bookingId;
        this.waiterId = builder.waiterId;
        this.orderTime = builder.orderTime;
        this.status = builder.status;
    }


    public static class Builder {
        private int orderId;
        private int bookingId;
        private int waiterId;
        private LocalDateTime orderTime;
        private String status;

        public Builder orderId(int id) { this.orderId = id; return this; }
        public Builder bookingId(int id) { this.bookingId = id; return this; }
        public Builder waiterId(int id) { this.waiterId = id; return this; }
        public Builder orderTime(LocalDateTime time) { this.orderTime = time; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public OrderTable build() { return new OrderTable(this); }
    }

    // Getters...
    public int getOrderId() {
        return orderId;
    }
    public int getBookingId() {
        return bookingId;
    }
    public int getWaiterId() {
        return waiterId;
    }
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
    public String getStatus() {
        return status;
    }

    //setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderTable{" +
                "orderId=" + orderId +
                ", bookingId=" + bookingId +
                ", waiterId=" + waiterId +
                ", orderTime=" + orderTime +
                ", status='" + status + '\'' +
                '}';
    }


}

