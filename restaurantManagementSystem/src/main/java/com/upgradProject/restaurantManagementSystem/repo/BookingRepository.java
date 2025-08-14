package com.upgradProject.restaurantManagementSystem.repo;

import com.upgradProject.restaurantManagementSystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Integer> {}

