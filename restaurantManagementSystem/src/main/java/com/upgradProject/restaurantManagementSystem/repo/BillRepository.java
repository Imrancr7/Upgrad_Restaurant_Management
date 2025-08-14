package com.upgradProject.restaurantManagementSystem.repo;

import com.upgradProject.restaurantManagementSystem.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
}
