package org.restaurant.model;

public class Employee {
    private final int employeeId;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String password;

    private Employee(Builder builder) {
        this.employeeId = builder.employeeId;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.role = builder.role;
        this.password = builder.password;
    }

    public static class Builder {
        private int employeeId;
        private String name;
        private String email;
        private String phone;
        private String role;
        private String password;

        public Builder employeeId(int id) { this.employeeId = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder role(String role) { this.role = role; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Employee build() { return new Employee(this); }
    }

    // Getters...
    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
