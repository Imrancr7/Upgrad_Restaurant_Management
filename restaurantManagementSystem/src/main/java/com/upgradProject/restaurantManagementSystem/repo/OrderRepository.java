package com.upgradProject.restaurantManagementSystem.repo;

import com.upgradProject.restaurantManagementSystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}

