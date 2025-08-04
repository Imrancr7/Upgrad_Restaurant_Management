package org.restaurant.model;

public class MenuItem {
    private final int itemId;
    private final String name;
    private final String description;
    private final double price;
    private final boolean available;

    private MenuItem(Builder builder) {
        this.itemId = builder.itemId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.available = builder.available;
    }

    public static class Builder {
        private int itemId;
        private String name;
        private String description;
        private double price;
        private boolean available = true;

        public Builder itemId(int id) { this.itemId = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String desc) { this.description = desc; return this; }
        public Builder price(double price) { this.price = price; return this; }
        public Builder available(boolean available) { this.available = available; return this; }
        public MenuItem build() { return new MenuItem(this); }
    }

    // Getters...
    public int getItemId() {
        return itemId;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public boolean isAvailable() {
        return available;
    }
    @Override
    public String toString() {
        return "MenuItem{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
