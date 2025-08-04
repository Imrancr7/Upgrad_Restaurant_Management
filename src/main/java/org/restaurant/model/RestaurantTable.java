package org.restaurant.model;

public class RestaurantTable {
    private final int tableId;
    private final String tableNumber;
    private final int capacity;
    private final boolean isAvailable;

    private RestaurantTable(Builder builder) {
        this.tableId = builder.tableId;
        this.tableNumber = builder.tableNumber;
        this.capacity = builder.capacity;
        this.isAvailable = builder.isAvailable;
    }

    public static class Builder {
        private int tableId;
        private String tableNumber;
        private int capacity;
        private boolean isAvailable = true;

        public Builder tableId(int id) { this.tableId = id; return this; }
        public Builder tableNumber(String number) { this.tableNumber = number; return this; }
        public Builder capacity(int capacity) { this.capacity = capacity; return this; }
        public Builder isAvailable(boolean available) { this.isAvailable = available; return this; }
        public RestaurantTable build() { return new RestaurantTable(this); }
    }

    // Getters...
    public int getTableId() {
        return tableId;
    }
    public String getTableNumber() {
        return tableNumber;
    }
    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "tableId=" + tableId +
                ", tableNumber='" + tableNumber + '\'' +
                ", capacity=" + capacity +
                ", isAvailable=" + isAvailable +
                '}';
    }


}


