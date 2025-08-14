package com.upgradProject.restaurantManagementSystem.service;

import com.upgradProject.restaurantManagementSystem.dto.OrderItemDTO;
import com.upgradProject.restaurantManagementSystem.dto.OrderRequestDTO;
import com.upgradProject.restaurantManagementSystem.dto.OrderResponseDTO;
import com.upgradProject.restaurantManagementSystem.entity.*;
import com.upgradProject.restaurantManagementSystem.enums.OrderStatus;
import com.upgradProject.restaurantManagementSystem.repo.OrderItemRepository;
import com.upgradProject.restaurantManagementSystem.repo.OrderRepository;
import com.upgradProject.restaurantManagementSystem.service.impls.OrderServiceImpl;
import com.upgradProject.restaurantManagementSystem.service.interfaces.BookingService;
import com.upgradProject.restaurantManagementSystem.service.interfaces.EmployeeService;
import com.upgradProject.restaurantManagementSystem.service.interfaces.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookingService bookingService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private MenuItemService menuItemService;
    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        reset(orderRepository, bookingService, employeeService, menuItemService, orderItemRepository);
    }

    @Test
    void placeOrder_SavesOrderAndOrderItemsAndReturnsResponse() {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setMenuItemId(1);
        itemDTO.setQuantity(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setBookingId(10);
        requestDTO.setWaiterId(20);
        requestDTO.setItems(List.of(itemDTO));

        Booking booking = new Booking();
        booking.setId(10);
        Employee waiter = new Employee();
        waiter.setId(20);
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1);

        Order order = new Order();
        order.setId(100);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        when(bookingService.getBookingById(10)).thenReturn(booking);
        when(employeeService.getEmployeeById(20)).thenReturn(waiter);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(100);
            return o;
        });
        when(menuItemService.getMenuItemById(1)).thenReturn(menuItem);
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDTO response = orderService.placeOrder(requestDTO);

        assertThat(response.getOrderId()).isEqualTo(100);
        assertThat(response.getBookingId()).isEqualTo(10);
        assertThat(response.getWaiterId()).isEqualTo(20);
        assertThat(response.getStatus()).isEqualTo(OrderStatus.PLACED.name());
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).save(any(OrderItem.class));
    }

    @Test
    void placeOrder_ThrowsExceptionWhenBookingNotFound() {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setMenuItemId(1);
        itemDTO.setQuantity(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setBookingId(999);
        requestDTO.setWaiterId(20);
        requestDTO.setItems(List.of(itemDTO));

        when(bookingService.getBookingById(999)).thenThrow(new RuntimeException("Booking not found"));

        assertThatThrownBy(() -> orderService.placeOrder(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Booking not found");
        verify(bookingService).getBookingById(999);
    }

    @Test
    void placeOrder_ThrowsExceptionWhenWaiterNotFound() {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setMenuItemId(1);
        itemDTO.setQuantity(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setBookingId(10);
        requestDTO.setWaiterId(999);
        requestDTO.setItems(List.of(itemDTO));

        Booking booking = new Booking();
        booking.setId(10);

        when(bookingService.getBookingById(10)).thenReturn(booking);
        when(employeeService.getEmployeeById(999)).thenThrow(new RuntimeException("Waiter not found"));

        assertThatThrownBy(() -> orderService.placeOrder(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Waiter not found");
        verify(employeeService).getEmployeeById(999);
    }

    @Test
    void placeOrder_ThrowsExceptionWhenMenuItemNotFound() {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setMenuItemId(999);
        itemDTO.setQuantity(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setBookingId(10);
        requestDTO.setWaiterId(20);
        requestDTO.setItems(List.of(itemDTO));

        Booking booking = new Booking();
        booking.setId(10);
        Employee waiter = new Employee();
        waiter.setId(20);

        when(bookingService.getBookingById(10)).thenReturn(booking);
        when(employeeService.getEmployeeById(20)).thenReturn(waiter);
        when(menuItemService.getMenuItemById(999)).thenThrow(new RuntimeException("Menu item not found"));

        assertThatThrownBy(() -> orderService.placeOrder(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Menu item not found");
        verify(menuItemService).getMenuItemById(999);
    }

    @Test
    void getAllOrders_ReturnsListOfOrderResponseDTOs() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOrderId()).isEqualTo(3);
        assertThat(result.get(0).getBookingId()).isEqualTo(1);
        assertThat(result.get(0).getWaiterId()).isEqualTo(2);
        assertThat(result.get(0).getStatus()).isEqualTo(OrderStatus.PLACED.name());
        verify(orderRepository).findAll();
    }

    @Test
    void getAllOrders_ReturnsEmptyListWhenNoOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertThat(result).isEmpty();
        verify(orderRepository).findAll();
    }

    @Test
    void updateOrderStatus_UpdatesStatusAndReturnsResponse() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        when(orderRepository.findById(3)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderResponseDTO response = orderService.updateOrderStatus(3, "SERVED");

        assertThat(response.getOrderId()).isEqualTo(3);
        assertThat(response.getStatus()).isEqualTo(OrderStatus.SERVED.name());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_ThrowsExceptionWhenOrderNotFound() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrderStatus(99, "CANCELLED"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Order not found");
        verify(orderRepository).findById(99);
    }

    @Test
    void updateOrderStatus_ThrowsExceptionForInvalidStatus() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        when(orderRepository.findById(3)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.updateOrderStatus(3, "INVALID_STATUS"))
                .isInstanceOf(IllegalArgumentException.class);
        verify(orderRepository).findById(3);
    }

    @Test
    void deleteOrder_DeletesOrderAndOrderItemsWhenStatusPlaced() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(5);
        orderItem.setOrder(order);

        when(orderRepository.findById(3)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(List.of(orderItem));

        orderService.deleteOrder(3);

        verify(orderItemRepository).findByOrder(order);
        verify(orderItemRepository).delete(orderItem);
        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrder_DeletesOrderAndOrderItemsWhenStatusCancelled() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.CANCELLED);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(5);
        orderItem.setOrder(order);

        when(orderRepository.findById(3)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(List.of(orderItem));

        orderService.deleteOrder(3);

        verify(orderItemRepository).findByOrder(order);
        verify(orderItemRepository).delete(orderItem);
        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrder_ThrowsExceptionWhenOrderNotFound() {
        when(orderRepository.findById(77)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.deleteOrder(77))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Order not found");
        verify(orderRepository).findById(77);
    }

    @Test
    void deleteOrder_ThrowsExceptionWhenOrderStatusInKitchen() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.IN_KITCHEN);

        when(orderRepository.findById(3)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.deleteOrder(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only orders with status PLACED or CANCELLED can be deleted");
        verify(orderRepository).findById(3);
    }

    @Test
    void deleteOrder_ThrowsExceptionWhenOrderStatusServed() {
        Booking booking = new Booking();
        booking.setId(1);
        Employee waiter = new Employee();
        waiter.setId(2);
        Order order = new Order();
        order.setId(3);
        order.setBooking(booking);
        order.setWaiter(waiter);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.SERVED);

        when(orderRepository.findById(3)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.deleteOrder(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only orders with status PLACED or CANCELLED can be deleted");
        verify(orderRepository).findById(3);
    }
}