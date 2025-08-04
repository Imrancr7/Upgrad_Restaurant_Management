package org.restaurant.model;

public class Customer {
    private int customerId;
    private final String name;
    private final String phone;
    private final String email;

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
    }

    public static class Builder {
        private int customerId;
        private String name;
        private String phone;
        private String email;

        public Builder customerId(int id) { this.customerId = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Customer build() { return new Customer(this); }
    }

    // Getters...
    public int getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }

    // Setter for customerId
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
