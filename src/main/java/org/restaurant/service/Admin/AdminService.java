package org.restaurant.service.Admin;

import org.restaurant.model.MenuItem;
import org.restaurant.model.RestaurantTable;

public interface AdminService {

    RestaurantTable addTable(String tableNumber, int capacity);
    void updateTable(int tableId, String tableNumber, int capacity);
    void deleteTable(int tableId);
    MenuItem addMenuItem(String name, String description, double price, boolean available);
    void updateMenuItem(int itemId, String name, String description, double price, boolean available);
    void deleteMenuItem(int itemId);

}
