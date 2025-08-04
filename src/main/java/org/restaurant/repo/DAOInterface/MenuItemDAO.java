package org.restaurant.repo.DAOInterface;

import org.restaurant.model.MenuItem;

import java.util.List;

public interface MenuItemDAO {
    MenuItem addMenuItem(MenuItem item);
    MenuItem getMenuItemById(int itemId);
    List<MenuItem> getAllMenuItems();
    void updateMenuItem(MenuItem item);
    void deleteMenuItem(int itemId);

    List<MenuItem> getAvailableMenuItems();
}
