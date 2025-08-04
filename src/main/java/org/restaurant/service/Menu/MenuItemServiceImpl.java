package org.restaurant.service.Menu;


import org.restaurant.model.MenuItem;
import org.restaurant.repo.DAOInterface.MenuItemDAO;
import org.restaurant.repo.DAOImpl.MenuItemDAOImpl;
import java.util.List;

public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemDAO menuItemDAO;

    public MenuItemServiceImpl() {
        this.menuItemDAO = new MenuItemDAOImpl();
    }

    @Override
    public MenuItem createMenuItem(MenuItem item) {
        return menuItemDAO.addMenuItem(item);
    }

    @Override
    public MenuItem getMenuItemById(int id) {
        return menuItemDAO.getMenuItemById(id);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemDAO.getAllMenuItems();
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        return menuItemDAO.getAvailableMenuItems();
    }

    @Override
    public void updateMenuItem(MenuItem item) {
        menuItemDAO.updateMenuItem(item);
    }

    @Override
    public void deleteMenuItem(int id) {
        menuItemDAO.deleteMenuItem(id);
    }

    @Override
    public void showMenu() {
        List<MenuItem> menuItems = menuItemDAO.getAllMenuItems();
        if (menuItems.isEmpty()) {
            System.out.println("Menu is currently empty.");
        } else {
            System.out.println("----- FULL MENU -----");
            for (MenuItem item : menuItems) {
                System.out.printf("ID: %d | Name: %s | ₹%.2f | Available: %s\n",
                        item.getItemId(),
                        item.getName(),
                        item.getPrice(),
                        item.isAvailable() ? "Yes" : "No");
            }
        }
    }
}

