package org.restaurant;


import org.restaurant.model.Booking;
import org.restaurant.model.Customer;
import org.restaurant.model.OrderItem;
import org.restaurant.model.RestaurantTable;
import org.restaurant.service.Bill.BillService;
import org.restaurant.service.Bill.BillServiceImpl;
import org.restaurant.service.Booking.BookingService;
import org.restaurant.service.Booking.BookingServiceImpl;
import org.restaurant.service.Customer.CustomerService;
import org.restaurant.service.Customer.CustomerServiceImpl;
import org.restaurant.service.Menu.MenuItemService;
import org.restaurant.service.Menu.MenuItemServiceImpl;
import org.restaurant.service.Order.OrderService;
import org.restaurant.service.Order.OrderServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CustomerService customerService = new CustomerServiceImpl();
        BookingService bookingService = new BookingServiceImpl();
        MenuItemService menuItemService = new MenuItemServiceImpl();
        BillService billService = new BillServiceImpl();
        OrderService orderService = new OrderServiceImpl(billService);
        System.out.println(customerService.getAllCustomers());
        Scanner in  = new Scanner(System.in);
        int customerId;
//
         //create new customer
        System.out.println("Welcome to the Restaurant Booking System!");
        System.out.println("If already customer, enter yes or no to register as a new customer");
        String response = in.nextLine();
        if(response.equalsIgnoreCase("yes")) {
            System.out.println("Enter Your name:");
            String name = in.nextLine();
            System.out.println("Enter your email:");
            String email = in.nextLine();
            Customer customer = customerService.registerCustomer(new Customer.Builder()
                    .name(name)
                    .email(email)
                    .phone("1234567890") // Default phone number for example
                    .build());

            customerId = customer.getCustomerId();
            System.out.println(customer);
            System.out.println("Customer registered successfully! Note the id for future reference: " + customer.getCustomerId());
        }
        else {
            System.out.println("Enter booking details for the customer:");
            System.out.println("Enter customer ID:");
            customerId = in.nextInt();
            in.nextLine();
        }

        List<RestaurantTable> restaurantTables = customerService.getAvailableTables();
        System.out.println("Available tables:");
        for (RestaurantTable table : restaurantTables) {
            System.out.println("Table ID: " + table);
        }



        System.out.println("Enter table id you want to book");
        int tableId = in.nextInt();
        in.nextLine(); // Consume newline character
        Booking booking = bookingService.createBooking(new Booking.Builder()
                .customerId(customerId)
                .tableId(tableId)
                .bookingTime(LocalDateTime.now())
                .build());

        System.out.println(bookingService.createBooking(booking));
        System.out.println("Booking created successfully!");

        //show menu items
        menuItemService.showMenu();

        //build order items based on things user wants to order
        boolean ordering = true;
        List<OrderItem> orderItems = new ArrayList<>();
        while(ordering){
            System.out.println("Do you want to order something? (yes/no)");
            String orderResponse = in.nextLine();
            if(orderResponse.equalsIgnoreCase("yes")) {
                System.out.println("Enter the item id you want to order:");
                int itemId = in.nextInt();
                in.nextLine(); // Consume newline character
                System.out.println("Enter the quantity of the item:");
                int quantity = in.nextInt();
                in.nextLine(); // Consume newline character
                OrderItem item = new OrderItem.Builder()
                        .itemId(itemId)
                        .quantity(quantity)
                        .build();
                orderItems.add(item);
                System.out.println("Item ordered successfully!");
            } else {
                ordering = false;
            }
        }
        int orderId = orderService.placeOrder(booking.getBookingId(), 1, orderItems);


        System.out.println("Order placed successfully! Your order ID is: " + orderId);
        System.out.println("Thank you for dining with us! Generating bill...");
        billService.generateBill(orderId);
        // Assuming orderId is 1 for simplicity


        //Retrieve booking details
        System.out.println("Enter booking ID to retrieve booking details:");
        int bookingId = in.nextInt();
        in.nextLine(); // Consume newline character
        Booking retrievedBooking = bookingService.getBookingById(bookingId);
        System.out.println(retrievedBooking);



        //See table availability after cancel or dining
        System.out.println("After the booking is done");
        bookingService.cancelBooking(retrievedBooking.getBookingId());
        System.out.println("Booking released successfully!");
        restaurantTables = customerService.getAvailableTables();
        System.out.println("Available tables:");
        for (RestaurantTable table : restaurantTables) {
            System.out.println("Table ID: " + table);
        }




    }
}
