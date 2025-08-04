package org.restaurant.service.Menu;

import org.restaurant.model.MenuItem;
import java.util.List;

public interface MenuItemService {
    MenuItem createMenuItem(MenuItem item);
    MenuItem getMenuItemById(int id);
    List<MenuItem> getAllMenuItems();
    List<MenuItem> getAvailableMenuItems();
    void updateMenuItem(MenuItem item);
    void deleteMenuItem(int id);
    void showMenu(); // displays entire menu
}
