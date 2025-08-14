package com.upgradProject.restaurantManagementSystem.service.impls;


import com.upgradProject.restaurantManagementSystem.controller.OrderController;
import com.upgradProject.restaurantManagementSystem.dto.OrderRequestDTO;
import com.upgradProject.restaurantManagementSystem.dto.OrderResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.*;
import com.upgradProject.restaurantManagementSystem.enums.OrderStatus;
import com.upgradProject.restaurantManagementSystem.repo.OrderItemRepository;
import com.upgradProject.restaurantManagementSystem.repo.OrderRepository;
import com.upgradProject.restaurantManagementSystem.service.interfaces.BookingService;
import com.upgradProject.restaurantManagementSystem.service.interfaces.EmployeeService;
import com.upgradProject.restaurantManagementSystem.service.interfaces.MenuItemService;
import com.upgradProject.restaurantManagementSystem.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookingService bookingService;
    private final EmployeeService employeeService;
    private final MenuItemService menuItemService;
    private final OrderItemRepository orderItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    public OrderServiceImpl(OrderRepository orderRepository, BookingService bookingService,
                            EmployeeService employeeService, MenuItemService menuItemService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.bookingService = bookingService;
        this.employeeService = employeeService;
        this.menuItemService = menuItemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {
        Booking booking = bookingService.getBookingById(dto.getBookingId());
        Employee waiter = employeeService.getEmployeeById(dto.getWaiterId());

        Order order = new Order();
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        orderRepository.save(order);
        logger.info("Order placed successfully with ID: {}", order.getId());

        dto.getItems().forEach(itemDto -> {
            MenuItem menuItem = menuItemService.getMenuItemById(itemDto.getMenuItemId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItemRepository.save(orderItem);
        });
        logger.info("Order items added successfully for Order ID: {}", order.getId());
        return convertToResponseDTO(order);
    }


    @Override
    public List<OrderResponseDTO> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));
        order = orderRepository.save(order);
        logger.info("Order status updated successfully for Order ID: {}", order.getId());
        return convertToResponseDTO(order);
    }

    @Override
    public void deleteOrder(int orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if(OrderStatus.IN_KITCHEN.equals(order.getStatus()) || OrderStatus.SERVED.equals(order.getStatus())) {
            logger.error("Cannot delete order with status: {}", order.getStatus());
            throw new IllegalArgumentException("Only orders with status PLACED or CANCELLED can be deleted");
        }
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        orderItems.forEach(orderItemRepository::delete);
        logger.info("Deleting order with ID: {}", order.getId());
        orderRepository.delete(order);
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .bookingId(order.getBooking().getId())
                .waiterId(order.getWaiter().getId())
                .status(order.getStatus().name())
                .orderTime(order.getOrderTime())
                .build();
    }
}