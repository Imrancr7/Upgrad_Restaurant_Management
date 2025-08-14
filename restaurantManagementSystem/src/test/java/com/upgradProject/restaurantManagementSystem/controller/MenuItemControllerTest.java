package com.upgradProject.restaurantManagementSystem.controller;

import com.upgradProject.restaurantManagementSystem.entity.MenuItem;
import com.upgradProject.restaurantManagementSystem.service.interfaces.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemController menuItemController;

    @BeforeEach
    void setUp() {
        reset(menuItemService);
    }

    @Test
    void getAllMenuItems_ReturnsListOfMenuItems() {
        List<MenuItem> menuItems = Arrays.asList(new MenuItem(), new MenuItem());
        when(menuItemService.getAllMenuItems()).thenReturn(menuItems);

        ResponseEntity<List<MenuItem>> result = menuItemController.getAllMenuItems();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).containsExactlyElementsOf(menuItems);
        verify(menuItemService).getAllMenuItems();
    }

    @Test
    void getAllMenuItems_ReturnsEmptyListWhenNoMenuItems() {
        when(menuItemService.getAllMenuItems()).thenReturn(Collections.emptyList());

        ResponseEntity<List<MenuItem>> result = menuItemController.getAllMenuItems();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEmpty();
        verify(menuItemService).getAllMenuItems();
    }

    @Test
    void getMenuItemById_ReturnsMenuItemWhenFound() {
        int id = 1;
        MenuItem menuItem = new MenuItem();
        when(menuItemService.getMenuItemById(id)).thenReturn(menuItem);

        ResponseEntity<MenuItem> result = menuItemController.getMenuItemById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(menuItem);
        verify(menuItemService).getMenuItemById(id);
    }

    @Test
    void getMenuItemById_ReturnsNullWhenMenuItemNotFound() {
        int id = 99;
        when(menuItemService.getMenuItemById(id)).thenReturn(null);

        ResponseEntity<MenuItem> result = menuItemController.getMenuItemById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNull();
        verify(menuItemService).getMenuItemById(id);
    }
}