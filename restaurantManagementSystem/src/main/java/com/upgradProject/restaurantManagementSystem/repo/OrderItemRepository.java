package com.upgradProject.restaurantManagementSystem.repo;

import com.upgradProject.restaurantManagementSystem.entity.Order;
import com.upgradProject.restaurantManagementSystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}

