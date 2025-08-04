package org.restaurant.repo.DAOInterface;

import org.restaurant.model.RestaurantTable;

import java.util.List;

public interface RestaurantTableDAO {
    RestaurantTable addTable(RestaurantTable table);
    RestaurantTable getTableById(int tableId);
    List<RestaurantTable> getAllTables();
    void updateTable(RestaurantTable table);
    void deleteTable(int tableId);

    List<RestaurantTable> getAvailableTables();
}
