package org.restaurant.service.Admin;

import org.restaurant.model.MenuItem;
import org.restaurant.model.RestaurantTable;
import org.restaurant.repo.DAOInterface.RestaurantTableDAO;
import org.restaurant.service.Menu.MenuItemService;

public class AdminServiceImpl implements AdminService{

    private final MenuItemService menuItemService;
    private final RestaurantTableDAO restaurantTableDAO;
    public AdminServiceImpl(MenuItemService menuItemService, RestaurantTableDAO restaurantTableDAO) {
        this.menuItemService = menuItemService;
        this.restaurantTableDAO = restaurantTableDAO;
    }


    @Override
    public RestaurantTable addTable(String tableNumber, int capacity) {
        RestaurantTable restaurantTable = new RestaurantTable.Builder()
                .tableNumber(tableNumber)
                .capacity(capacity)
                .build();
        return restaurantTableDAO.addTable(restaurantTable);
    }

    @Override
    public void updateTable(int tableId, String tableNumber, int capacity) {
        restaurantTableDAO.updateTable(new RestaurantTable.Builder()
                .tableId(tableId)
                .tableNumber(tableNumber)
                .capacity(capacity)
                .build());
    }

    @Override
    public void deleteTable(int tableId) {
        restaurantTableDAO.deleteTable(tableId);
    }

    @Override
    public MenuItem addMenuItem(String name, String description, double price, boolean available) {
        MenuItem menuItem = new MenuItem.Builder()
                .name(name)
                .description(description)
                .price(price)
                .available(available)
                .build();
        return menuItemService.createMenuItem(menuItem);
    }

    @Override
    public void updateMenuItem(int itemId, String name, String description, double price, boolean available) {
        MenuItem menuItem = new MenuItem.Builder()
                .itemId(itemId)
                .name(name)
                .description(description)
                .price(price)
                .available(available)
                .build();
        menuItemService.updateMenuItem(menuItem);
    }

    @Override
    public void deleteMenuItem(int itemId) {
        menuItemService.deleteMenuItem(itemId);
    }
}
