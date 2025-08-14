package com.upgradProject.restaurantManagementSystem.service.interfaces;

import com.upgradProject.restaurantManagementSystem.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItem> getAllMenuItems();
    MenuItem getMenuItemById(int id);
}
