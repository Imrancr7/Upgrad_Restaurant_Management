package com.upgradProject.restaurantManagementSystem.service.impls;

import com.upgradProject.restaurantManagementSystem.controller.OrderController;
import com.upgradProject.restaurantManagementSystem.entity.MenuItem;
import com.upgradProject.restaurantManagementSystem.exceptions.MenuItemNotAvailable;
import com.upgradProject.restaurantManagementSystem.repo.MenuItemRepository;
import com.upgradProject.restaurantManagementSystem.service.interfaces.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        logger.info("Fetching all menu items");
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getMenuItemById(int id) {
        logger.info("Fetching menu item with id: {}", id);
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotAvailable("MenuItem is not available id : " + id));
    }
}
