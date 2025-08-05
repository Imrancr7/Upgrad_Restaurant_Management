# Restaurant Management System

A comprehensive Java-based restaurant management system that handles table bookings, customer management, menu items, orders, and billing.

## Features

- **Customer Management**: Register new customers and maintain customer profiles
- **Table Booking**: Manage table reservations and availability
- **Menu Management**: Create and maintain menu items with descriptions and prices
- **Order Processing**: Take and track customer orders 
- **Billing System**: Generate and manage bills for customer orders
- **Payment Processing**: Handle various payment methods
- **Employee Management**: Track employee information and roles

## System Requirements

- Java Development Kit (JDK) 17 or higher
- PostgreSQL Database
- Maven for dependency management

## Project Setup

### Database Configuration

1. Install PostgreSQL if not already installed
2. Create a new database for the restaurant management system
3. Configure your database connection in `src/main/resources/application.properties`:

```properties
db.url=jdbc:postgresql://localhost:5432/your_database_name
db.user=your_username
db.password=your_password
```

### Building the Project

```bash
# Clone the repository 
git clone https://github.com/Imrancr7/Upgrad_Restaurant_Management.git
cd restaurant-management

# Build with Maven
mvn clean package
```

### Running the Application

```bash

java -jar target/restaurant-management-1.0-SNAPSHOT.jar
```

## Project Structure

- **config/**: Contains database configuration
- **enums/**: Enumeration classes used throughout the project
- **model/**: Data models representing core entities (Customer, Booking, MenuItem, etc.)
- **repo/**: Data Access Objects (DAOs) for database operations
  - **DAOImpl/**: Implementation of DAO interfaces
  - **DAOInterface/**: Interfaces defining data access operations
- **service/**: Business logic for different functionalities
  - **Admin/**: Admin-specific operations
  - **Bill/**: Billing operations
  - **Booking/**: Table reservation operations
  - **Customer/**: Customer management operations
  - **Menu/**: Menu management operations
  - **Order/**: Order processing operations
- **diagrams/**: PlantUML diagrams for system architecture visualization

## Design Patterns Used

- **Builder Pattern**: Used in model classes to create immutable objects with many optional parameters
- **DAO Pattern**: Separates persistence logic from business logic
- **Service Layer Pattern**: Encapsulates business logic separate from controllers and data access

## Database Schema

The system uses a relational database with the following main tables:
- Customers
- Bookings
- RestaurantTables
- MenuItems
- OrderItems
- OrderTables
- Bills
- Payments
- Employees

# ER Diagram
```mermaid
erDiagram
    CUSTOMER {
        int customer_id PK
        varchar name
        varchar phone
        varchar email
    }

    RESTAURANT_TABLE {
        int table_id PK
        varchar table_number
        int capacity
        boolean is_available
    }

    BOOKING {
        int booking_id PK
        int customer_id FK
        int table_id FK
        timestamp booking_time
        varchar status
    }

    EMPLOYEE {
        int employee_id PK
        varchar name
        varchar email
        varchar phone
        varchar role
        varchar password
    }

    MENU_ITEM {
        int item_id PK
        varchar name
        text description
        decimal price
        boolean available
    }

    ORDER_TABLE {
        int order_id PK
        int booking_id FK
        int waiter_id FK
        timestamp order_time
        varchar status
    }

    ORDER_ITEM {
        int order_item_id PK
        int order_id FK
        int item_id FK
        int quantity
    }

    BILL {
        int bill_id PK
        int order_id FK
        decimal total_amount
        timestamp generated_time
        varchar status
    }

    PAYMENT {
        int payment_id PK
        int bill_id FK
        timestamp payment_time
        decimal amount
        varchar payment_mode
    }

    %% Relationships
    CUSTOMER ||--o{ BOOKING : makes
    RESTAURANT_TABLE ||--o{ BOOKING : reserves
    BOOKING ||--o{ ORDER_TABLE : has
    EMPLOYEE ||--o{ ORDER_TABLE : handles
    ORDER_TABLE ||--o{ ORDER_ITEM : contains
    MENU_ITEM ||--o{ ORDER_ITEM : appears_in
    ORDER_TABLE ||--|| BILL : generates
    BILL ||--|| PAYMENT : paid_by
```

# Class Diagram
```mermaid
classDiagram
    class Customer {
        int customer_id
        String name
        String phone
        String email
    }

    class RestaurantTable {
        int table_id
        String table_number
        int capacity
        boolean is_available
    }

    class Booking {
        int booking_id
        int customer_id
        int table_id
        datetime booking_time
        String status
    }

    class Employee {
        int employee_id
        String name
        String email
        String phone
        String role
        String password
    }

    class OrderTable {
        int order_id
        int booking_id
        int waiter_id
        datetime order_time
        String status
    }

    class MenuItem {
        int item_id
        String name
        String description
        decimal price
        boolean available
    }

    class OrderItem {
        int order_item_id
        int order_id
        int item_id
        int quantity
    }

    class Bill {
        int bill_id
        int order_id
        decimal total_amount
        datetime generated_time
        String status
    }

    class Payment {
        int payment_id
        int bill_id
        datetime payment_time
        decimal amount
        String payment_mode
    }

    %% Relationships
    Customer "1" --> "0..*" Booking
    RestaurantTable "1" --> "0..*" Booking
    Booking "1" --> "0..1" OrderTable
    Employee "1" --> "0..*" OrderTable : waiter
    OrderTable "1" --> "0..*" OrderItem
    MenuItem "1" --> "0..*" OrderItem
    OrderTable "1" --> "0..1" Bill
    Bill "1" --> "0..1" Payment
```
# Flow Diagram
```mermaid
flowchart TD
    A[Start] --> B{Is Customer Already Registered?}
    B -- Yes --> C[Enter Customer ID]
    B -- No --> D[Register New Customer]
    D --> E[Get Customer ID]

    C --> F[Show Available Tables]
    E --> F

    F --> G[Select Table ID to Book]
    G --> H[Create Booking]
    H --> I[Show Menu Items]

    I --> J{Want to Order Items?}
    J -- Yes --> K[Enter Item ID & Quantity]
    K --> J
    J -- No --> L[Place Order]

    L --> M[Generate Bill]
    M --> N[Pay Bill]
    N --> O[Retrieve Booking by ID]
    O --> P[Release Booking]
    P --> Q[Show Updated Available Tables]
    Q --> R[End]
```

# Table Booking - Sequence Diagram
```mermaid
sequenceDiagram
    participant Customer
    participant BookingService
    participant TableService
    participant DB

    Customer->>BookingService: Request to book table (customerId, tableId, time)
    BookingService->>TableService: checkAvailability(tableId)
    TableService->>DB: SELECT * FROM restaurant_table WHERE id = ?
    DB-->>TableService: Table data
    TableService-->>BookingService: isAvailable = true
    BookingService->>DB: INSERT INTO booking (...)
    DB-->>BookingService: Booking ID
    BookingService-->>Customer: Booking confirmed (booking ID)
```
# Order Placement Flow - Sequence Diagram
```mermaid
sequenceDiagram
    participant Customer
    participant Waiter
    participant OrderService
    participant MenuService
    participant Kitchen
    participant DB

    Customer->>Waiter: I want to order (itemId, quantity)
    Waiter->>MenuService: validateItem(itemId)
    MenuService->>DB: SELECT * FROM menu_item WHERE item_id = ?
    DB-->>MenuService: Menu item
    MenuService-->>Waiter: Item available

    Waiter->>OrderService: placeOrder(bookingId, waiterId, orderItems)
    OrderService->>DB: INSERT INTO order_table (...)
    DB-->>OrderService: orderId
    OrderService->>DB: INSERT INTO order_item (...)
    DB-->>OrderService: Insert success
    OrderService-->>Kitchen: New order for preparation
    Kitchen-->>OrderService: Order received
    OrderService-->>Waiter: Order confirmed (orderId)
    Waiter-->>Customer: Order placed successfully
```
# Bill Generation Flow - Sequence Diagram
```mermaid
sequenceDiagram
    participant Customer
    participant BillService
    participant OrderService
    participant DB

    Customer->>BillService: Request bill for orderId
    BillService->>OrderService: calculateTotal(orderId)
    OrderService->>DB: SELECT * FROM order_item WHERE order_id = ?
    DB-->>OrderService: Item list
    OrderService->>DB: SELECT price FROM menu_item WHERE item_id IN (...)
    DB-->>OrderService: Prices
    OrderService-->>BillService: Total amount

    BillService->>DB: INSERT INTO bill (...)
    DB-->>BillService: billId
    BillService-->>Customer: Bill generated

    Customer->>BillService: Make payment
    BillService->>DB: INSERT INTO payment (billId, amount, time, mode)
    DB-->>BillService: Payment saved
    BillService-->>Customer: Payment confirmed
```

