package org.restaurant.model;

public class OrderItem {
    private final int orderItemId;
    private int orderId;
    private final int itemId;
    private final int quantity;

    private OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.orderId = builder.orderId;
        this.itemId = builder.itemId;
        this.quantity = builder.quantity;
    }



    public static class Builder {
        private int orderItemId;
        private int orderId;
        private int itemId;
        private int quantity;

        public Builder orderItemId(int id) { this.orderItemId = id; return this; }
        public Builder orderId(int id) { this.orderId = id; return this; }
        public Builder itemId(int id) { this.itemId = id; return this; }
        public Builder quantity(int qty) { this.quantity = qty; return this; }
        public OrderItem build() { return new OrderItem(this); }
    }

    // Getters...
    public int getOrderItemId() {
        return orderItemId;
    }
    public int getOrderId() {
        return orderId;
    }
    public int getItemId() {
        return itemId;
    }
    public int getQuantity() {
        return quantity;
    }

    //setter for orderId
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }




}

