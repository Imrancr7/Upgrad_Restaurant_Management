package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.entity.MenuItem;
import com.upgradProject.restaurantManagementSystem.exceptions.GlobalExceptionHandler;
import com.upgradProject.restaurantManagementSystem.service.interfaces.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable int id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }
}

